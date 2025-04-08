package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ClimbSubsystem;

public class ClimberOutCommandGroup extends SequentialCommandGroup {
    public ClimberOutCommandGroup(ClimbSubsystem m_climbSubsystem){
        addCommands(
            new RunCommand(() -> m_climbSubsystem.setServo(0.7)).withTimeout(0.5),
            new ClimberOutCommand(m_climbSubsystem)
        );
    }
}

