package frc.robot.commands.ElevatorWristCommands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.Constants.MotionConstants;

public class L2ScoreCommandGroup extends SequentialCommandGroup {
    public L2ScoreCommandGroup(ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem){
        addCommands(
            new ParallelCommandGroup(
                new WristPositionCommand(m_wristSubsystem, MotionConstants.l2.get("wrist").doubleValue()),
                new SequentialCommandGroup(
                    Commands.waitSeconds(0.3),
                    new ElevatorPositionCommand(m_elevatorSubsystem, MotionConstants.l2.get("elevator").doubleValue())
                )
                

            )
        );
    }
}

