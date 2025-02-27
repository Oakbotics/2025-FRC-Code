package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.FieldConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.WristSubsystem;

public class Middle3Piece extends SequentialCommandGroup {
  public Middle3Piece(
      DriveSubsystem m_driveSubsystem,
      ElevatorSubsystem m_elevatorSubsystem,
      WristSubsystem m_wristSubsystem,
      IntakeSubsystem m_intakeSubsystem) {
    addCommands(
        new InstantCommand(() -> m_driveSubsystem.setGyro(180.0)),
        new InstantCommand(
            () ->
                m_driveSubsystem.resetOdometry(
                    new Pose2d(
                        FieldConstants.reefPolePositions.get(10)[1].getX() + 1,
                        FieldConstants.reefPolePositions.get(10)[1].getY(),
                        Rotation2d.fromDegrees(180.0)))),
        new ParallelCommandGroup(
            m_driveSubsystem.findPathToPose(FieldConstants.reefPolePositions.get(10)[1]),
            new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)),
        new CoralOuttakeCommand(m_intakeSubsystem)
        // m_driveSubsystem.findPathToPose(FieldConstants.coralStationPosition.get(12)[0]),
        // m_driveSubsystem.findPathToPose(FieldConstants.reefBranchE),
        // m_driveSubsystem.findPathToPose(FieldConstants.coralStationPosition.get(12)[0]),
        // m_driveSubsystem.findPathToPose(FieldConstants.reefBranchF)
        );
  }
}
