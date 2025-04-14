package frc.robot.commands.ElevatorWristCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

public class L2AlgaeCommandGroup extends SequentialCommandGroup {
    public L2AlgaeCommandGroup(ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem){
        addCommands(
            new WristPositionCommand(m_wristSubsystem, 80),
            new ElevatorPositionCommand(m_elevatorSubsystem, 0.16)
        );
    }
}

