package frc.robot.commands.LimeLightAutos;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.Constants.FieldConstants;
import frc.robot.commands.CoralOuttakeCommand;
import frc.robot.commands.L4ScoreCommandGroup;

public class Middle1PieceLL extends SequentialCommandGroup {
    public Middle1PieceLL(DriveSubsystem m_driveSubsystem, ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem, IntakeSubsystem m_intakeSubsystem, LimeLightSubsystem m_limelightSubsystem){
        if (DriverStation.getAlliance().get() == Alliance.Blue)
        addCommands(
            new RunCommand(() -> m_driveSubsystem.gyroLimelightReset()).withTimeout(0.1),
            new RunCommand(() -> m_driveSubsystem.limeLightPoseUpdate()).withTimeout(0.1),
            new ParallelCommandGroup(
                m_driveSubsystem.findPathToPose(FieldConstants.reefPolePositions.get(21)[0]), 
                new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
            ),
            new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(1)
        );
    else 
    addCommands(
            new RunCommand(() -> m_driveSubsystem.setGyro(0)).withTimeout(0.1),
            new RunCommand(() -> m_driveSubsystem.limeLightPoseUpdate()).withTimeout(0.1),
            new ParallelCommandGroup(
                m_driveSubsystem.findPathToPose(FieldConstants.reefPolePositions.get(10)[0]), 
                new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
            ),
            new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(1)
    );
    }
}