package frc.robot.commands.LimeLightAutos;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
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

public class Right3PieceLL extends SequentialCommandGroup {
    public Right3PieceLL(DriveSubsystem m_driveSubsystem, ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem, IntakeSubsystem m_intakeSubsystem){
        if(DriverStation.getAlliance().get() == Alliance.Blue)
            addCommands(
                new RunCommand(() -> m_driveSubsystem.gyroLimelightReset()).withTimeout(0.01),
                new RunCommand(() -> m_driveSubsystem.limeLightPoseUpdate()).withTimeout(0.01),
                new ParallelCommandGroup(
                    new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                    m_driveSubsystem.findPathToPose(FieldConstants.reefPolePositions.get(22)[1])
                ),
                new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(0.2),
                new RunCommand(() -> m_driveSubsystem.limeLightPoseUpdate()).withTimeout(0.01),
                new ParallelCommandGroup(
                    new IntakeCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                    m_driveSubsystem.findPathToPose(FieldConstants.coralStationPosition.get(12)[0])
                ),
                new CoralIntakeCommand(m_intakeSubsystem).withTimeout(0.2),
                new ParallelCommandGroup(
                    new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                    m_driveSubsystem.findPathToPose(FieldConstants.reefPolePositions.get(17)[0])
                ),
                new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(0.2),
                new RunCommand(() -> m_driveSubsystem.limeLightPoseUpdate()).withTimeout(0.01),
                new ParallelCommandGroup(
                    new IntakeCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                    m_driveSubsystem.findPathToPose(FieldConstants.coralStationPosition.get(12)[0])
                ),
                new CoralIntakeCommand(m_intakeSubsystem).withTimeout(0.2),
                new ParallelCommandGroup(
                    new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                    m_driveSubsystem.findPathToPose(FieldConstants.reefPolePositions.get(17)[1])
                ),
                new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(0.2),
                new RunCommand(() -> m_driveSubsystem.limeLightPoseUpdate()).withTimeout(0.01)
            );
        else
            addCommands(
                new RunCommand(() -> m_driveSubsystem.gyroLimelightReset()).withTimeout(0.01),
                new RunCommand(() -> m_driveSubsystem.limeLightPoseUpdate()).withTimeout(0.01),
                new ParallelCommandGroup(
                    new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                    m_driveSubsystem.findPathToPose(FieldConstants.reefPolePositions.get(9)[1])
                ),
                new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(0.2),
                new RunCommand(() -> m_driveSubsystem.limeLightPoseUpdate()).withTimeout(0.01),
                new ParallelCommandGroup(
                    new IntakeCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                    m_driveSubsystem.findPathToPose(FieldConstants.coralStationPosition.get(2)[0])
                ),
                new CoralIntakeCommand(m_intakeSubsystem).withTimeout(0.2),
                new ParallelCommandGroup(
                    new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                    m_driveSubsystem.findPathToPose(FieldConstants.reefPolePositions.get(8)[0])
                ),
                new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(0.2),
                new RunCommand(() -> m_driveSubsystem.limeLightPoseUpdate()).withTimeout(0.01),
                new ParallelCommandGroup(
                    new IntakeCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                    m_driveSubsystem.findPathToPose(FieldConstants.coralStationPosition.get(2)[0])
                ),
                new CoralIntakeCommand(m_intakeSubsystem).withTimeout(0.2),
                new ParallelCommandGroup(
                    new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                    m_driveSubsystem.findPathToPose(FieldConstants.reefPolePositions.get(8)[1])
                ),
                new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(0.2),
                new RunCommand(() -> m_driveSubsystem.limeLightPoseUpdate()).withTimeout(0.01)
            );
    }
}

