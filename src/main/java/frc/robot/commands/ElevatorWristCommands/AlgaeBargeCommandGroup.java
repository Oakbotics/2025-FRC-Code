package frc.robot.commands.ElevatorWristCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.Constants.MotionConstants;
import frc.robot.subsystems.ElevatorSubsystem;

public class AlgaeBargeCommandGroup extends SequentialCommandGroup {
    public AlgaeBargeCommandGroup(ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem){
        addCommands(
            new ElevatorPositionCommand(m_elevatorSubsystem, MotionConstants.algaeBarge.get("elevator").doubleValue()),
            new WristPositionCommand(m_wristSubsystem, MotionConstants.algaeBarge.get("wrist").doubleValue())
            
        );
    }
}