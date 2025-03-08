package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.Constants.MotionConstants;

public class L3ScoreCommandGroup extends SequentialCommandGroup {
    public L3ScoreCommandGroup(ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem){
        addCommands(
            new WristPositionCommand(m_wristSubsystem, MotionConstants.l3.get("wrist").doubleValue())
                .onlyIf(() -> (
                    m_elevatorSubsystem.getElevatorHeight() > MotionConstants.l4.get("elevator").doubleValue() - 0.03 
                    || m_elevatorSubsystem.getElevatorHeight() < MotionConstants.stowed.get("elevator").doubleValue() + 0.03
                )
            ),
            new IntakeClearanceOutCommand(m_elevatorSubsystem, m_wristSubsystem)
                .onlyIf(() -> (
                    m_elevatorSubsystem.getElevatorHeight() < (MotionConstants.intake.get("elevator").doubleValue() + 0.03)
                    && m_elevatorSubsystem.getElevatorHeight() > (MotionConstants.intake.get("elevator").doubleValue() - 0.03)
                )
            ),
            new WristPositionCommand(m_wristSubsystem, MotionConstants.l3.get("wrist").doubleValue()),
            new ElevatorPositionCommand(m_elevatorSubsystem, MotionConstants.l3.get("elevator").doubleValue())
        );
    }
}

