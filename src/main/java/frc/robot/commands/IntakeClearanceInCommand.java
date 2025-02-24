package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.Constants.MotionConstants;
import frc.robot.subsystems.ElevatorSubsystem;

public class IntakeClearanceInCommand extends SequentialCommandGroup {
    public IntakeClearanceInCommand(ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem){
        addCommands(           
            new ElevatorPositionCommand(m_elevatorSubsystem, MotionConstants.intakeClearanceIn.get("elevator").doubleValue()),
            new WristPositionCommand(m_wristSubsystem, MotionConstants.intakeClearanceIn.get("wrist").doubleValue())
        );
    }
}