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
import frc.robot.subsystems.WristSubsystem;
import frc.robot.Constants.FieldConstants;
import frc.robot.commands.CoralIntakeCommand;
import frc.robot.commands.CoralOuttakeCommand;
import frc.robot.commands.IntakeCommandGroup;
import frc.robot.commands.L4ScoreCommandGroup;

public class Left3PieceLL extends SequentialCommandGroup {
    public Left3PieceLL(DriveSubsystem m_driveSubsystem, ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem, IntakeSubsystem m_intakeSubsystem){
        if(DriverStation.getAlliance().get() == Alliance.Blue)
            addCommands(
                new RunCommand(() -> m_driveSubsystem.gyroLimelightReset()).withTimeout(0.05),
                Commands.waitSeconds(0.2),
                new RunCommand(() -> m_driveSubsystem.resetPoseLL()).withTimeout(0.01),
                new ParallelCommandGroup(
                    new SequentialCommandGroup(
                        Commands.waitSeconds(0.5),
                        new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                    ),
                    m_driveSubsystem.findPathToPole(false)
                ),
                new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(0.1),
                new ParallelCommandGroup(
                    new SequentialCommandGroup(
                        Commands.waitSeconds(0.5),
                        new IntakeCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                        ),
                    m_driveSubsystem.findPathToPose(FieldConstants.coralStationPosition.get(13)[1])
                ),
                new CoralIntakeCommand(m_intakeSubsystem).withTimeout(0.3),
                new ParallelCommandGroup(
                    new SequentialCommandGroup(
                        Commands.waitSeconds(0.5),
                        new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                    ),
                    new CoralIntakeCommand(m_intakeSubsystem).withTimeout(0.8),
                    m_driveSubsystem.findPathToPole(false)

                ),
                new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(0.1),
                new ParallelCommandGroup(
                    new SequentialCommandGroup(
                        Commands.waitSeconds(0.5),
                        new IntakeCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                        ),
                    m_driveSubsystem.findPathToPose(FieldConstants.coralStationPosition.get(13)[1])
                ),
                new CoralIntakeCommand(m_intakeSubsystem).withTimeout(0.3),
                new ParallelCommandGroup(
                    new SequentialCommandGroup(
                        Commands.waitSeconds(0.5),
                        new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                    ),
                    new CoralIntakeCommand(m_intakeSubsystem).withTimeout(0.8),
                    m_driveSubsystem.findPathToPole(true)

                ),
                new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(0.1)
            );
        else
            addCommands(
                new RunCommand(() -> m_driveSubsystem.gyroLimelightReset()).withTimeout(0.05),
                Commands.waitSeconds(0.2),
                new RunCommand(() -> m_driveSubsystem.resetPoseLL()).withTimeout(0.01),
                new ParallelCommandGroup(
                    new SequentialCommandGroup(
                        Commands.waitSeconds(0.5),
                        new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                    ),
                    m_driveSubsystem.findPathToPole(false)
                ),
                new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(0.1),
                new ParallelCommandGroup(
                    new SequentialCommandGroup(
                        Commands.waitSeconds(0.5),
                        new IntakeCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                        ),
                    m_driveSubsystem.findPathToPose(FieldConstants.coralStationPosition.get(1)[1])
                ),
                new CoralIntakeCommand(m_intakeSubsystem).withTimeout(0.3),
                new ParallelCommandGroup(
                    new SequentialCommandGroup(
                        Commands.waitSeconds(0.5),
                        new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                    ),
                    new CoralIntakeCommand(m_intakeSubsystem).withTimeout(0.8),
                    m_driveSubsystem.findPathToPole(false)
                ),
                new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(0.1),
                new ParallelCommandGroup(
                    new SequentialCommandGroup(
                        Commands.waitSeconds(0.5),
                        new IntakeCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                        ),
                    m_driveSubsystem.findPathToPose(FieldConstants.coralStationPosition.get(1)[1])
                ),
                new CoralIntakeCommand(m_intakeSubsystem).withTimeout(0.3),
                new ParallelCommandGroup(
                    new SequentialCommandGroup(
                        Commands.waitSeconds(0.5),
                        new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                    ),
                    new CoralIntakeCommand(m_intakeSubsystem).withTimeout(0.8),
                    m_driveSubsystem.findPathToPole(true)

                ),
                new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(0.1)
            );
    }
}

