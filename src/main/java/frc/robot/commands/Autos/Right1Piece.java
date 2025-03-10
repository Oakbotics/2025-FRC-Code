package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.Constants.FieldConstants;
import frc.robot.commands.CoralOuttakeCommand;
import frc.robot.commands.L4ScoreCommandGroup;

public class Right1Piece extends SequentialCommandGroup {
    public Right1Piece(DriveSubsystem m_driveSubsystem, ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem, IntakeSubsystem m_intakeSubsystem){
        addCommands(
            new InstantCommand(() -> m_driveSubsystem.setGyro(180.0)),
            new InstantCommand(() -> m_driveSubsystem.resetOdometry(FieldConstants.bottomStartingPose)),
            new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
            m_driveSubsystem.findPathToPose(FieldConstants.reefPolePositions.get(22)[0]),
            new CoralOuttakeCommand(m_intakeSubsystem)
            // m_driveSubsystem.findPathToPose(FieldConstants.coralStationPosition.get(12)[0]),
            // m_driveSubsystem.findPathToPose(FieldConstants.reefBranchE),
            // m_driveSubsystem.findPathToPose(FieldConstants.coralStationPosition.get(12)[0]),
            // m_driveSubsystem.findPathToPose(FieldConstants.reefBranchF)
        );
    }
}

