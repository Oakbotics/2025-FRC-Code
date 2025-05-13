package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;


public class ShootNoteCommandGroup extends SequentialCommandGroup {
    public ShootNoteCommandGroup(ShooterSubsystem m_ShooterSubsystem, ConveyorSubsystem m_ConveyorSubsystem){
        addCommands(
            new SequentialCommandGroup(
                new ShootCommand(m_ShooterSubsystem).withTimeout(2.5),
                new IntakeCommand(m_ConveyorSubsystem)
            )
        );
    }
}
