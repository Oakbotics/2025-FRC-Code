// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants.FieldConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ExampleSubsystem;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class GoToPoseCommand extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem m_driveSubsystem;
  private final boolean m_isLeft;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public GoToPoseCommand(DriveSubsystem driveSubsystem, boolean isLeft) {
    m_driveSubsystem = driveSubsystem;
    m_isLeft = isLeft;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Pose2d nearestPolePose = new Pose2d();
    double nearestPolePoseDistance = 100000;
    Pose2d botpose = m_driveSubsystem.getPose();
    double[] distances = new double[24];
    if(DriverStation.getAlliance().get() == Alliance.Red){
      for(int i = 6; i < 12; i++){
        Pose2d polePose = FieldConstants.reefPolePositions.get(i)[m_isLeft ? 0 : 1];
        double distance = Math.sqrt(Math.pow(botpose.getX() - polePose.getX(), 2) + Math.pow(botpose.getY() - polePose.getY(), 2));
        distances[i] = distance;
        if(distance < nearestPolePoseDistance){
          nearestPolePoseDistance = distance;
          nearestPolePose = polePose;
        }
      }
    }
    else if(DriverStation.getAlliance().get() == Alliance.Blue){
      for(int i = 17; i < 23; i++){
        Pose2d polePose = FieldConstants.reefPolePositions.get(i)[m_isLeft ? 0 : 1];
        double distance = Math.sqrt((botpose.getX() - polePose.getX()) * (botpose.getX() - polePose.getX()) + (botpose.getY() - polePose.getY())*(botpose.getY() - polePose.getY()));
        distances[i] = distance;
        if(distance < nearestPolePoseDistance){
          nearestPolePoseDistance = distance;
          nearestPolePose = polePose;
        }
      }
    }
    m_driveSubsystem.findPathToPose(nearestPolePose).schedule();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
