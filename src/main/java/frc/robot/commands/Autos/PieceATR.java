package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.commands.AutoAlignCommands.AutoAlignToReefTagRelative;
import frc.robot.commands.ElevatorWristCommands.CoralOuttakeCommand;
import frc.robot.commands.ElevatorWristCommands.L4ScoreCommandGroup;

public class PieceATR extends SequentialCommandGroup {
    public PieceATR(DriveSubsystem m_driveSubsystem, ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem, IntakeSubsystem m_intakeSubsystem, LimeLightSubsystem m_limelightSubsystem){
            addCommands(
                new RunCommand(() -> m_driveSubsystem.gyroLimelightReset()).withTimeout(0.2),
                new RunCommand(() -> m_driveSubsystem.resetPoseLL()).withTimeout(0.2),
                Commands.waitSeconds(2),
                new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                new AutoAlignToReefTagRelative(false, m_driveSubsystem, m_limelightSubsystem).withTimeout(7),
                new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(1)
            );
    }
}