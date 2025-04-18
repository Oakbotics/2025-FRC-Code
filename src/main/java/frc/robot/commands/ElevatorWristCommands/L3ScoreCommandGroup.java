package frc.robot.commands.ElevatorWristCommands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.Constants.MotionConstants;

public class L3ScoreCommandGroup extends SequentialCommandGroup {
    public L3ScoreCommandGroup(ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem){
        addCommands(
            new ParallelCommandGroup(
                new WristPositionCommand(m_wristSubsystem, MotionConstants.l3.get("wrist").doubleValue()),
                new ElevatorPositionCommand(m_elevatorSubsystem, MotionConstants.l3.get("elevator").doubleValue())
            )
        );
    }
}

