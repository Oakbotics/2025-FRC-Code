package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ClimbSubsystem;

public class ClimberOutCommandGroup extends SequentialCommandGroup {
    public ClimberOutCommandGroup(ClimbSubsystem m_climbSubsystem){
        addCommands( 
            new ClimberUnlockCommand(m_climbSubsystem).withTimeout(1),
            new ClimberOutCommand(m_climbSubsystem)
        );
    }
}