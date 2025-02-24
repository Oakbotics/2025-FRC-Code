package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.Constants.MotionConstants;
import frc.robot.subsystems.ElevatorSubsystem;

public class L4ScoreCommandGroup extends ParallelCommandGroup {
    public L4ScoreCommandGroup(ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem){
        addCommands(
            new WristPositionCommand(m_wristSubsystem, 180.0).onlyIf(() -> (m_elevatorSubsystem.getElevatorHeight() < 0.01)),
            new IntakeClearanceOutCommand(m_elevatorSubsystem, m_wristSubsystem)
                .onlyIf(() -> (
                    (
                        m_elevatorSubsystem.getElevatorHeight() < (MotionConstants.intake.get("elevator").doubleValue() + 0.03)
                        && m_elevatorSubsystem.getElevatorHeight() > (MotionConstants.intake.get("elevator").doubleValue() - 0.03)
                    )
            )),
            new ElevatorPositionCommand(m_elevatorSubsystem, MotionConstants.l4.get("elevator").doubleValue()),
            new WristPositionCommand(m_wristSubsystem, MotionConstants.l4.get("wrist").doubleValue())
        );
    }
}