package frc.robot.commands.ElevatorWristCommands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

public class AlgaeBargeCommandGroup extends ParallelCommandGroup {
    public AlgaeBargeCommandGroup(ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem){
        addCommands(
            new ElevatorPositionCommand(m_elevatorSubsystem, 1.49),
            new WristPositionCommand(m_wristSubsystem, 100).withTimeout(2)
        );
    }
}