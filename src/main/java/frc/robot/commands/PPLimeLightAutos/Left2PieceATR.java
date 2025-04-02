package frc.robot.commands.PPLimeLightAutos;

import edu.wpi.first.apriltag.AprilTag;
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
import frc.robot.commands.AlignToReefTagRelative;
import frc.robot.commands.CoralIntakeCommand;
import frc.robot.commands.CoralOuttakeCommand;
import frc.robot.commands.IntakeCommandGroup;
import frc.robot.commands.L4ScoreCommandGroup;

public class Left2PieceATR extends SequentialCommandGroup {
    public Left2PieceATR(DriveSubsystem m_driveSubsystem, ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem, IntakeSubsystem m_intakeSubsystem, LimeLightSubsystem m_limelightSubsystem){
        if(DriverStation.getAlliance().get() == Alliance.Blue)
            addCommands(
                new RunCommand(() -> m_driveSubsystem.gyroLimelightReset()).withTimeout(0.1),
                new RunCommand(() -> m_driveSubsystem.limeLightPoseUpdate()).withTimeout(0.1),
                new ParallelCommandGroup(
                    new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                ),
                new AlignToReefTagRelative(true, m_driveSubsystem, m_limelightSubsystem).withTimeout(5),
                new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(1),
                new IntakeCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                m_driveSubsystem.findPathToPose(FieldConstants.coralStationPosition.get(13)[1]),
                new CoralIntakeCommand(m_intakeSubsystem).withTimeout(0.3),
                new AlignToReefTagRelative(false, m_driveSubsystem, m_limelightSubsystem),
                new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
            );
        else
        addCommands(
                new RunCommand(() -> m_driveSubsystem.gyroLimelightReset()).withTimeout(0.1),
                new RunCommand(() -> m_driveSubsystem.limeLightPoseUpdate()).withTimeout(0.1),
                m_driveSubsystem.findPathToPose(FieldConstants.aprilTagPosition.get(11)),
                new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                new AlignToReefTagRelative(true, m_driveSubsystem, m_limelightSubsystem).withTimeout(1),
                new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(0.3),
                new ParallelCommandGroup( 
                    new SequentialCommandGroup(
                        Commands.waitSeconds(0.3),
                        new IntakeCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                    ),
                    m_driveSubsystem.findPathToPose(FieldConstants.coralStationPosition.get(1)[1])
                ),
                new CoralIntakeCommand(m_intakeSubsystem).withTimeout(0.3),
                m_driveSubsystem.findPathToPose(FieldConstants.aprilTagPosition.get(6)),
                new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem),
                new AlignToReefTagRelative(false, m_driveSubsystem, m_limelightSubsystem).withTimeout(1),
               new CoralOuttakeCommand(m_intakeSubsystem).withTimeout(0.3),
               new ParallelCommandGroup( 
                    new SequentialCommandGroup(
                        Commands.waitSeconds(0.3),
                        new IntakeCommandGroup(m_elevatorSubsystem, m_wristSubsystem)
                    ),
                    m_driveSubsystem.findPathToPose(FieldConstants.coralStationPosition.get(1)[1])
                )
            );
    }
}