package frc.robot.commands.Autos;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.Constants.FieldConstants;
import frc.robot.commands.CoralOuttakeCommand;
import frc.robot.commands.L4ScoreCommandGroup;

public class Middle1Piece extends SequentialCommandGroup {
    public Middle1Piece(DriveSubsystem m_driveSubsystem, ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem, IntakeSubsystem m_intakeSubsystem){
        addCommands(
            new RunCommand(() -> m_driveSubsystem.setGyro(0) ).withTimeout(0.1), 
            new RunCommand(() -> m_driveSubsystem.resetOdometry(new Pose2d(0, 0, Rotation2d.fromDegrees(0)))).withTimeout(0.1),
            m_driveSubsystem.findPathToPose(new Pose2d(3, 0, Rotation2d.fromDegrees((0))))


            
        );
    }
}

 