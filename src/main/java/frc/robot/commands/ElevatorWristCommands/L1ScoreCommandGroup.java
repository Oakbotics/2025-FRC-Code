package frc.robot.commands.ElevatorWristCommands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.Constants.MotionConstants;

public class L1ScoreCommandGroup extends SequentialCommandGroup {
    public L1ScoreCommandGroup(ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem){
        addCommands(
            // new WristPositionCommand(m_wristSubsystem, MotionConstants.l2.get("wrist").doubleValue())
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
            new ParallelCommandGroup(
                new WristPositionCommand(m_wristSubsystem, MotionConstants.l1.get("wrist").doubleValue()),
                new SequentialCommandGroup(
                    Commands.waitSeconds(0.3),
                    new ElevatorPositionCommand(m_elevatorSubsystem, MotionConstants.l1.get("elevator").doubleValue())
                )
                

            )
        );
    }
}

