// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DriveConstants;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;

public class AutoAlignToReefTagRelative extends Command {
  private PIDController xController, yController, rotController;
  private boolean isRight;
  private Timer dontSeeTagTimer, stopTimer;
  private DriveSubsystem m_driveSubsystem;
  private LimeLightSubsystem m_limeLightSubsystem;
  private double tagID = -1;
  private double X_SETPOINT_REEF_ALIGNMENT; // to be found
  private double Y_LEFT_SETPOINT_REEF_ALIGNMENT; // to be found
  private double Y_RIGHT_SETPOINT_REEF_ALIGNMENT; // to be found
  private double ROT_SETPOINT_REEF_ALIGNMENT;

  private double X_TOLERANCE_REEF_ALIGNMENT = 0.01;
  private double Y_TOLERANCE_REEF_ALIGNMENT = 0.01;
  private double ROT_TOLERANCE_REEF_ALIGNMENT = 3.5;

  private String limelightUsed;
  double[] positions;

  public AutoAlignToReefTagRelative(boolean isRight, DriveSubsystem m_driveSubsystem, LimeLightSubsystem m_limeLightSubsystem) {
    xController = new PIDController(DriveConstants.kXP,DriveConstants.kXI, DriveConstants.kXD);
    yController = new PIDController(DriveConstants.kYP,DriveConstants.kYI, DriveConstants.kYD);
    rotController = new PIDController(DriveConstants.kRP,DriveConstants.kRI, DriveConstants.kRD);

    rotController.enableContinuousInput(-180, 180);
    this.isRight = isRight;
    this.m_driveSubsystem = m_driveSubsystem;
    this.m_limeLightSubsystem = m_limeLightSubsystem;
    addRequirements(m_driveSubsystem, m_limeLightSubsystem);
  }

  @Override
  public void initialize() {
    if(m_limeLightSubsystem.getRightID() != -1 && isRight == false){
      limelightUsed = "limelight-right";
    }
    else if(m_limeLightSubsystem.getLeftID() != -1 && isRight == true){
      limelightUsed = "limelight-left";

    }
      X_SETPOINT_REEF_ALIGNMENT = -0.43;
      Y_LEFT_SETPOINT_REEF_ALIGNMENT = 0.19; 
      Y_RIGHT_SETPOINT_REEF_ALIGNMENT = -0.18; 
      ROT_SETPOINT_REEF_ALIGNMENT = 0.0; //-2.67;

    this.stopTimer = new Timer();
    this.stopTimer.start();
    this.dontSeeTagTimer = new Timer();
    this.dontSeeTagTimer.start();

    // rotController.setSetpoint(ROT_SETPOINT_REEF_ALIGNMENT);
    rotController.setTolerance(ROT_TOLERANCE_REEF_ALIGNMENT);

    // xController.setSetpoint(X_SETPOINT_REEF_ALIGNMENT);
    xController.setTolerance(X_TOLERANCE_REEF_ALIGNMENT);

    // yController.setSetpoint(isLeft ? Y_LEFT_SETPOINT_REEF_ALIGNMENT : Y_RIGHT_SETPOINT_REEF_ALIGNMENT);
    yController.setTolerance(Y_TOLERANCE_REEF_ALIGNMENT);

    tagID = LimelightHelpers.getFiducialID(limelightUsed);
    // SmartDashboard.putString("limelightUsed", limelightUsed);
    // SmartDashboard.putNumber("tagID", tagID);
  }

  @Override
  public void execute() {
    if (LimelightHelpers.getTV(limelightUsed) && LimelightHelpers.getFiducialID(limelightUsed) == tagID) {

      if(DriverStation.getAlliance().get() == Alliance.Red)
        LimelightHelpers.setPipelineIndex(limelightUsed, 0);
      else
        LimelightHelpers.setPipelineIndex(limelightUsed, 1);

      SmartDashboard.putNumber("pipeline index", LimelightHelpers.getCurrentPipelineIndex(limelightUsed));
        
      this.dontSeeTagTimer.reset();

      positions = LimelightHelpers.getBotPose_TargetSpace(limelightUsed);

      double xSpeed = xController.calculate(positions[2], X_SETPOINT_REEF_ALIGNMENT);
      // SmartDashboard.putNumber("xspeed", xSpeed);
      double ySpeed = 0;
      if(isRight){
        ySpeed = -yController.calculate(positions[0],Y_LEFT_SETPOINT_REEF_ALIGNMENT);
        SmartDashboard.putNumber("Goal AutoAlign Y", Y_LEFT_SETPOINT_REEF_ALIGNMENT);
      }
      else{
        ySpeed = -yController.calculate(positions[0],Y_RIGHT_SETPOINT_REEF_ALIGNMENT);
        SmartDashboard.putNumber("Goal AutoAlign Y", Y_RIGHT_SETPOINT_REEF_ALIGNMENT);
      }
      double rotValue = -rotController.calculate(positions[4], ROT_SETPOINT_REEF_ALIGNMENT);

      SmartDashboard.putNumber("Goal AutoAlign X", X_SETPOINT_REEF_ALIGNMENT);
      SmartDashboard.putNumber("Goal AutoAlign Rotation", ROT_SETPOINT_REEF_ALIGNMENT);

      SmartDashboard.putNumber("Current AutoAlign X", positions[2]);
      SmartDashboard.putNumber("Current AutoAlign Y", positions[0]);
      SmartDashboard.putNumber("Current AutoAlign Rotation", positions[4]);

      m_driveSubsystem.autoDrive(xSpeed, ySpeed, rotValue, false);

      if (!rotController.atSetpoint() ||
          !yController.atSetpoint() ||
          !xController.atSetpoint()
          ) {
        stopTimer.reset();
      }
    } else {
      m_driveSubsystem.autoDrive(0,0, 0, false);
    }

    // SmartDashboard.putNumber("poseValidTimer", stopTimer.get());
  }

  @Override
  public void end(boolean interrupted) {
    m_driveSubsystem.autoDrive(0,0, 0, false);
  }

  @Override
  public boolean isFinished() {
    // Requires the robot to stay in the correct position for 0.3 seconds, as long as it gets a tag in the camera
    if(positions != null)
      return Math.abs(positions[2] - X_SETPOINT_REEF_ALIGNMENT) < X_TOLERANCE_REEF_ALIGNMENT
      && (
          Math.abs(positions[0] - Y_LEFT_SETPOINT_REEF_ALIGNMENT) < Y_TOLERANCE_REEF_ALIGNMENT
          || Math.abs(positions[0] - Y_RIGHT_SETPOINT_REEF_ALIGNMENT) < Y_TOLERANCE_REEF_ALIGNMENT
        )
        && Math.abs(positions[4] - ROT_SETPOINT_REEF_ALIGNMENT) < ROT_TOLERANCE_REEF_ALIGNMENT;
    else{
      return false;
    }
  }
}