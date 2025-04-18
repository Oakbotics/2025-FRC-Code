package frc.robot.commands.ElevatorWristCommands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.Constants.MotionConstants;
import frc.robot.subsystems.ElevatorSubsystem;

public class L4ScoreCommandGroup extends SequentialCommandGroup {
    public L4ScoreCommandGroup(ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem){
        addCommands(
            new ParallelCommandGroup(
                new ElevatorPositionCommand(m_elevatorSubsystem, MotionConstants.l4.get("elevator").doubleValue()),
                new WristPositionCommand(m_wristSubsystem, MotionConstants.l4.get("wrist").doubleValue())
            )
            
        );
    }
}