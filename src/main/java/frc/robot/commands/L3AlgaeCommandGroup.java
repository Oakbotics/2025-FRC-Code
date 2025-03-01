package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.Constants.MotionConstants;

public class L3AlgaeCommandGroup extends SequentialCommandGroup {
    public L3AlgaeCommandGroup(ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem){
        addCommands(
            new WristPositionCommand(m_wristSubsystem, 80),
            //     .onlyIf(() -> (
            //         m_elevatorSubsystem.getElevatorHeight() > (MotionConstants.l4.get("elevator").doubleValue() - 0.03) 
            //         || m_elevatorSubsystem.getElevatorHeight() < (MotionConstants.stowed.get("elevator").doubleValue() + 0.03)
            //     )
            // ),
            // new IntakeClearanceOutCommand(m_elevatorSubsystem, m_wristSubsystem)
            //     .onlyIf(() -> (
            //         m_elevatorSubsystem.getElevatorHeight() < (MotionConstants.intake.get("elevator").doubleValue() + 0.03)
            //         && m_elevatorSubsystem.getElevatorHeight() > (MotionConstants.intake.get("elevator").doubleValue() - 0.03)
            //     )
            // ),
            new ElevatorPositionCommand(m_elevatorSubsystem, 0.66)
        );
    }
}

