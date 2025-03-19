package frc.robot.commands.LimeLightAutos;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
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

public class Left2PieceLL extends SequentialCommandGroup {
    public Left2PieceLL(DriveSubsystem m_driveSubsystem, ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem, IntakeSubsystem m_intakeSubsystem){
        if(DriverStation.getAlliance().get() == Alliance.Blue)
            addCommands(
                new RunCommand(() -> m_driveSubsystem.gyroLimelightReset()).withTimeout(0.01),
                new RunCommand(() -> m_driveSubsystem.limeLightPoseUpdate()).withTimeout(0.01),
                new ParallelCommandGroup(
                    new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                    m_driveSubsystem.findPathToPose(FieldConstants.reefPolePositions.get(20)[1])
                ),
                new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(0.2),
                new RunCommand(() -> m_driveSubsystem.limeLightPoseUpdate()).withTimeout(0.01),
                new ParallelCommandGroup(
                    new IntakeCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                    m_driveSubsystem.findPathToPose(FieldConstants.coralStationPosition.get(13)[1])
                ),
                new CoralIntakeCommand(m_intakeSubsystem).withTimeout(0.2),
                new ParallelCommandGroup(
                    new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                    m_driveSubsystem.findPathToPose(FieldConstants.reefPolePositions.get(19)[0])
                ),
                new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(0.2),
                new RunCommand(() -> m_driveSubsystem.limeLightPoseUpdate()).withTimeout(0.01)
            );
        else
            addCommands(
                new RunCommand(() -> m_driveSubsystem.gyroLimelightReset()).withTimeout(0.01),
                m_driveSubsystem.findPathToPose(new Pose2d(m_driveSubsystem.getPose().getX(), m_driveSubsystem.getPose().getY(), Rotation2d.fromDegrees(-120))),
                new RunCommand(() -> m_driveSubsystem.limeLightPoseUpdate()).withTimeout(0.01),
                new ParallelCommandGroup(
                    new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                    m_driveSubsystem.findPathToPose(FieldConstants.reefPolePositions.get(11)[1])
                ),
                new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(0.2),
                new RunCommand(() -> m_driveSubsystem.limeLightPoseUpdate()).withTimeout(0.01),
                new ParallelCommandGroup(
                    new IntakeCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                    m_driveSubsystem.findPathToPose(FieldConstants.coralStationPosition.get(1)[1])
                ),
                new CoralIntakeCommand(m_intakeSubsystem).withTimeout(0.2),
                new ParallelCommandGroup(
                    new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                    m_driveSubsystem.findPathToPose(FieldConstants.reefPolePositions.get(6)[1])
                ),
                new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(0.2),
                new RunCommand(() -> m_driveSubsystem.limeLightPoseUpdate()).withTimeout(0.01)
            );
    }
}

