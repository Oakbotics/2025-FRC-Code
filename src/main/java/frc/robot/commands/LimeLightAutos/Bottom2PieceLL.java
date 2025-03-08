package frc.robot.commands.LimeLightAutos;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.Constants.FieldConstants;
import frc.robot.commands.CoralIntakeCommand;
import frc.robot.commands.CoralOuttakeCommand;
import frc.robot.commands.IntakeCommandGroup;
import frc.robot.commands.L4ScoreCommandGroup;

public class Bottom2PieceLL extends SequentialCommandGroup {
    public Bottom2PieceLL(DriveSubsystem m_driveSubsystem, ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem, IntakeSubsystem m_intakeSubsystem){
        addCommands(
            new InstantCommand(() -> m_driveSubsystem.setGyro(180.0)),
            new InstantCommand(() -> m_driveSubsystem.limeLightPoseUpdate()),
            new ParallelCommandGroup(
                new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                m_driveSubsystem.findPathToPose(FieldConstants.reefPolePositions.get(22)[0])
            ),
            new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(0.5),
            new InstantCommand(() -> m_driveSubsystem.limeLightPoseUpdate()),
            new ParallelCommandGroup(
                new IntakeCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                m_driveSubsystem.findPathToPose(FieldConstants.coralStationPosition.get(12)[0])
            ),
            new CoralIntakeCommand(m_intakeSubsystem).withTimeout(0.5),
            new ParallelCommandGroup(
                new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                m_driveSubsystem.findPathToPose(FieldConstants.reefPolePositions.get(17)[0])
            ),
            new CoralOuttakeCommand(m_intakeSubsystem),
            new InstantCommand(() -> m_driveSubsystem.limeLightPoseUpdate())
        );
    }
}

