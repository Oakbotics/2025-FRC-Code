package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

public class L2ScoreCommandGroup extends ParallelCommandGroup {
    public L2ScoreCommandGroup(ElevatorSubsystem m_elevatorSubsystem, ArmSubsystem m_armSubsystem){
        addCommands(
            new ArmL2L3Command(m_armSubsystem),
            // wait(250),
            new ElevatorL2Command(m_elevatorSubsystem)
        );
    }
}

