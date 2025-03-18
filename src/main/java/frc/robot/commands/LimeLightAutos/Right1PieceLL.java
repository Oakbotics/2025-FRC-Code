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
import frc.robot.commands.CoralOuttakeCommand;
import frc.robot.commands.L4ScoreCommandGroup;

public class Right1PieceLL extends SequentialCommandGroup {
    public Right1PieceLL(DriveSubsystem m_driveSubsystem, ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem, IntakeSubsystem m_intakeSubsystem){
        if(DriverStation.getAlliance().get() == Alliance.Blue)
            addCommands(
                new RunCommand(() -> m_driveSubsystem.gyroLimelightReset()).withTimeout(0.01),
                new RunCommand(() -> m_driveSubsystem.limeLightPoseUpdate()).withTimeout(0.01),
                new ParallelCommandGroup(
                    new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                    m_driveSubsystem.findPathToPose(FieldConstants.reefPolePositions.get(22)[1])
                ),
                new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(0.5),
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
                new RunCommand(() -> m_driveSubsystem.limeLightPoseUpdate()).withTimeout(0.01)
            );
    }
}

