package frc.robot.commands.Autos;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.Constants.FieldConstants;
import frc.robot.commands.CoralOuttakeCommand;
import frc.robot.commands.L4ScoreCommandGroup;

public class Middle1Piece extends SequentialCommandGroup {
    public Middle1Piece(DriveSubsystem m_driveSubsystem, ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem, IntakeSubsystem m_intakeSubsystem){
        addCommands(
            new InstantCommand(() -> m_driveSubsystem.setGyro(180.0)),
            new InstantCommand(() -> m_driveSubsystem.resetOdometry(FieldConstants.middleStartingPose)),
            new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
            m_driveSubsystem.findPathToPose(FieldConstants.reefPolePositions.get(21)[1]), 
            m_driveSubsystem.findPathToPose(new Pose2d(FieldConstants.reefPolePositions.get(21)[1].getX() + 0.15, FieldConstants.reefPolePositions.get(21)[1].getY(), FieldConstants.reefPolePositions.get(21)[1].getRotation())).withTimeout(5),
            new WaitCommand(5),
            new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(1)
            // m_driveSubsystem.findPathToPose(FieldConstants.coralStationPosition.get(12)[0]),
            // m_driveSubsystem.findPathToPose(FieldConstants.reefBranchE),
            // m_driveSubsystem.findPathToPose(FieldConstants.coralStationPosition.get(12)[0]),
            // m_driveSubsystem.findPathToPose(FieldConstants.reefBranchF)
        );
    }
}

 