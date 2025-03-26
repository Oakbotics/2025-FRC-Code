// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;

public class AlignToReefTagRelative extends Command {
  private PIDController xController, yController, rotController;
  private boolean isLeft;
  private Timer dontSeeTagTimer, stopTimer;
  private DriveSubsystem m_driveSubsystem;
  private LimeLightSubsystem m_limeLightSubsystem;
  private double tagID = -1;
  private double X_SETPOINT_REEF_ALIGNMENT = Units.inchesToMeters(6.5); // to be found
  private double Y_SETPOINT_REEF_ALIGNMENT = 0.44; // to be found
  private double ROT_SETPOINT_REEF_ALIGNMENT;

  private double X_TOLERANCE_REEF_ALIGNMENT;
  private double Y_TOLERANCE_REEF_ALIGNMENT;
  private double ROT_TOLERANCE_REEF_ALIGNMENT;

  private String limelightUsed;

  public AlignToReefTagRelative(boolean isLeft, DriveSubsystem m_driveSubsystem, LimeLightSubsystem m_limeLightSubsystem) {
    xController = new PIDController(0, 0.0, 0);  // Vertical movement
    yController = new PIDController(0, 0.0, 0);  // Horitontal movement
    rotController = new PIDController(0, 0, 0);  // Rotation
    this.isLeft = isLeft;
    this.m_driveSubsystem = m_driveSubsystem;
    this.m_limeLightSubsystem = m_limeLightSubsystem;
    addRequirements(m_driveSubsystem);
  }

  @Override
  public void initialize() {
    if(m_limeLightSubsystem.getLeftID() != 1){
      limelightUsed = "limelight-left";

      // X_SETPOINT_REEF_ALIGNMENT = 999999999;
      // Y_SETPOINT_REEF_ALIGNMENT = 999999999;
      // if(isLeft)
      //   ROT_SETPOINT_REEF_ALIGNMENT = 99999999;
      // else
      //   ROT_SETPOINT_REEF_ALIGNMENT = 99999999;
    }
    else if(m_limeLightSubsystem.getRightID() != 1){
      limelightUsed = "limelight-right";

      // X_SETPOINT_REEF_ALIGNMENT = 999999999;
      // Y_SETPOINT_REEF_ALIGNMENT = 999999999;
      // if(isLeft)
      //   ROT_SETPOINT_REEF_ALIGNMENT = 99999999;
      // else
      //   ROT_SETPOINT_REEF_ALIGNMENT = 99999999;
    }
    this.stopTimer = new Timer();
    this.stopTimer.start();
    this.dontSeeTagTimer = new Timer();
    this.dontSeeTagTimer.start();

    rotController.setSetpoint(ROT_SETPOINT_REEF_ALIGNMENT);
    rotController.setTolerance(ROT_TOLERANCE_REEF_ALIGNMENT);

    xController.setSetpoint(X_SETPOINT_REEF_ALIGNMENT);
    xController.setTolerance(X_TOLERANCE_REEF_ALIGNMENT);

    yController.setSetpoint(isLeft ? -Y_SETPOINT_REEF_ALIGNMENT : Y_SETPOINT_REEF_ALIGNMENT);
    yController.setTolerance(Y_TOLERANCE_REEF_ALIGNMENT);

    tagID = LimelightHelpers.getFiducialID(limelightUsed);
  }

  @Override
  public void execute() {
    if (LimelightHelpers.getTV(limelightUsed) && LimelightHelpers.getFiducialID(limelightUsed) == tagID) {
      this.dontSeeTagTimer.reset();

      double[] postions = LimelightHelpers.getBotPose_TargetSpace(limelightUsed);
      SmartDashboard.putNumber("x", postions[2]);

      double xSpeed = xController.calculate(postions[2]);
      SmartDashboard.putNumber("xspeed", xSpeed);
      double ySpeed = -yController.calculate(postions[0]);
      double rotValue = -rotController.calculate(postions[4]);

      m_driveSubsystem.drive(xSpeed, ySpeed, rotValue, false);

      if (!rotController.atSetpoint() ||
          !yController.atSetpoint() ||
          !xController.atSetpoint()) {
        stopTimer.reset();
      }
    } else {
      m_driveSubsystem.drive(0,0, 0, false);
    }

    SmartDashboard.putNumber("poseValidTimer", stopTimer.get());
  }

  @Override
  public void end(boolean interrupted) {
    m_driveSubsystem.drive(0,0, 0, false);
  }

  @Override
  public boolean isFinished() {
    // Requires the robot to stay in the correct position for 0.3 seconds, as long as it gets a tag in the camera
    return this.dontSeeTagTimer.hasElapsed(0.3) ||
        stopTimer.hasElapsed(0.3);
    // return false;
  }
}