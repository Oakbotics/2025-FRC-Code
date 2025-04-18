package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.Constants.FieldConstants;
import frc.robot.commands.AutoAlignCommands.AlgaeAlignToReefTagRelative;
import frc.robot.commands.AutoAlignCommands.AutoAlignToReefTagRelative;
import frc.robot.commands.ElevatorWristCommands.AlgaeBargeCommandGroup;
import frc.robot.commands.ElevatorWristCommands.AlgaeIntakeCommand;
import frc.robot.commands.ElevatorWristCommands.CoralOuttakeCommand;
import frc.robot.commands.ElevatorWristCommands.L2AlgaeCommandGroup;
import frc.robot.commands.ElevatorWristCommands.L3AlgaeCommandGroup;
import frc.robot.commands.ElevatorWristCommands.L3ScoreCommandGroup;
import frc.robot.commands.ElevatorWristCommands.L4ScoreCommandGroup;

public class AlgaePieceATR extends SequentialCommandGroup {
    public AlgaePieceATR(DriveSubsystem m_driveSubsystem, ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem, IntakeSubsystem m_intakeSubsystem, LimeLightSubsystem m_limelightSubsystem){
        if(DriverStation.getAlliance().get() == Alliance.Blue){
            addCommands(
                new RunCommand(() -> m_driveSubsystem.gyroLimelightReset()).withTimeout(0.1),
                new RunCommand(() -> m_driveSubsystem.resetPoseLL()).withTimeout(0.1),
                new ParallelCommandGroup(
                    m_driveSubsystem.findPathToPose(FieldConstants.aprilTagPosition.get(21)),
                    new L2AlgaeCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                ),
                new ParallelCommandGroup(
                    new AlgaeAlignToReefTagRelative(false , m_driveSubsystem, m_limelightSubsystem).withTimeout(1.5),
                    new AlgaeIntakeCommand(m_intakeSubsystem).withTimeout(2)
                ),
                new ParallelRaceGroup(
                    m_driveSubsystem.findPathToPose(FieldConstants.aprilTagPosition.get(14)),
                    new AlgaeIntakeCommand(m_intakeSubsystem)
                ),
                new AlgaeBargeCommandGroup(m_elevatorSubsystem, m_wristSubsystem, m_intakeSubsystem),
                new ParallelCommandGroup(
                    m_driveSubsystem.findPathToPose(FieldConstants.aprilTagPosition.get(21)),
                    new L3ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)

                ),
                new ParallelCommandGroup(
                    new AutoAlignToReefTagRelative(true, m_driveSubsystem, m_limelightSubsystem).withTimeout(1.5),
                    new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                ),
                new CoralOuttakeCommand(m_intakeSubsystem)
            );
        } else {
            addCommands(
                new RunCommand(() -> m_driveSubsystem.gyroLimelightReset()).withTimeout(0.1),
                new RunCommand(() -> m_driveSubsystem.resetPoseLL()).withTimeout(0.1),
                new ParallelCommandGroup(
                    m_driveSubsystem.findPathToPose(FieldConstants.aprilTagPosition.get(10)),
                    new L2AlgaeCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                ),
                new ParallelCommandGroup(
                    new AlgaeAlignToReefTagRelative(false , m_driveSubsystem, m_limelightSubsystem).withTimeout(1.5),
                    new AlgaeIntakeCommand(m_intakeSubsystem).withTimeout(2)
                ),
                new ParallelRaceGroup(
                    m_driveSubsystem.findPathToPose(FieldConstants.aprilTagPosition.get(5)),
                    new AlgaeIntakeCommand(m_intakeSubsystem)
                ),
                new AlgaeBargeCommandGroup(m_elevatorSubsystem, m_wristSubsystem, m_intakeSubsystem),
                new ParallelCommandGroup(
                    m_driveSubsystem.findPathToPose(FieldConstants.aprilTagPosition.get(10)),
                    new L3ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)

                ),
                new ParallelCommandGroup(
                    new AutoAlignToReefTagRelative(true, m_driveSubsystem, m_limelightSubsystem).withTimeout(1.5),
                    new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                ),
                new CoralOuttakeCommand(m_intakeSubsystem)
            );
        }
    }
}