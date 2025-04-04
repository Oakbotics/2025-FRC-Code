package frc.robot.commands.PPLimeLightAutos;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.Constants.FieldConstants;
import frc.robot.commands.AutoAlignToReefTagRelative;
import frc.robot.commands.CoralIntakeCommand;
import frc.robot.commands.CoralOuttakeCommand;
import frc.robot.commands.IntakeCommandGroup;
import frc.robot.commands.L4ScoreCommandGroup;

public class PractiseField extends SequentialCommandGroup {
    public PractiseField(DriveSubsystem m_driveSubsystem, ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem, IntakeSubsystem m_intakeSubsystem, LimeLightSubsystem m_limelightSubsystem){
        if(DriverStation.getAlliance().get() == Alliance.Blue)
            addCommands(
                new RunCommand(() -> m_driveSubsystem.gyroLimelightReset()).withTimeout(0.05),
                Commands.waitSeconds(0.2),
                new RunCommand(() -> m_driveSubsystem.resetPoseLL()).withTimeout(0.01),
                new ParallelCommandGroup(
                    // m_driveSubsystem.findPathToPose(FieldConstants.reefPolePositions.get(20)[1]), 
                    new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                ),
                new AutoAlignToReefTagRelative(false, m_driveSubsystem, m_limelightSubsystem).withTimeout(5),
                new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(1),
                new ParallelCommandGroup(
                    new SequentialCommandGroup(
                        Commands.waitSeconds(0.5),
                        new IntakeCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                        ),
                    m_driveSubsystem.findPathToPose(FieldConstants.coralStationPosition.get(13)[1])
                ),
                new CoralIntakeCommand(m_intakeSubsystem).withTimeout(1),
                new ParallelCommandGroup(
                    // m_driveSubsystem.findPathToPose(FieldConstants.reefPolePositions.get(20)[1]), 
                    new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                ),
                // new InstantCommand(() -> scheduler.set()),
                new AutoAlignToReefTagRelative(false, m_driveSubsystem, m_limelightSubsystem).withTimeout(5)
                // new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(1)
            );
        else
            addCommands(
                new RunCommand(() -> m_driveSubsystem.gyroLimelightReset()).withTimeout(0.05),
                Commands.waitSeconds(0.2),
                new RunCommand(() -> m_driveSubsystem.resetPoseLL()).withTimeout(0.01),
                new ParallelCommandGroup(
                    // m_driveSubsystem.findPathToPose(FieldConstants.reefPolePositions.get(20)[1]), 
                    new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                ),
                new AutoAlignToReefTagRelative(false, m_driveSubsystem, m_limelightSubsystem).withTimeout(5),
                new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(1),
                new ParallelCommandGroup(
                    new SequentialCommandGroup(
                        Commands.waitSeconds(0.5),
                        new IntakeCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                        ),
                    m_driveSubsystem.findPathToPose(FieldConstants.coralStationPosition.get(1)[1])
                ),
                new CoralIntakeCommand(m_intakeSubsystem).withTimeout(0.3),
                new ParallelCommandGroup(
                    // m_driveSubsystem.findPathToPose(FieldConstants.reefPolePositions.get(20)[1]), 
                    // new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                ),
                // new InstantCommand(() -> scheduler.set()),
                new AutoAlignToReefTagRelative(false, m_driveSubsystem, m_limelightSubsystem).withTimeout(5)
                // new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(1)
            );
    }
}

