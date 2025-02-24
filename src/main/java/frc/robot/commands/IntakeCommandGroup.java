package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.Constants.MotionConstants;
import frc.robot.subsystems.ElevatorSubsystem;

public class IntakeCommandGroup extends SequentialCommandGroup {
    public IntakeCommandGroup(ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem){
        addCommands(
            new WristPositionCommand(m_wristSubsystem, 180.0)
                .onlyIf(() -> (
                    m_elevatorSubsystem.getElevatorHeight() > MotionConstants.l4.get("elevator").doubleValue() - 0.03 
                    || m_elevatorSubsystem.getElevatorHeight() < MotionConstants.stowed.get("elevator").doubleValue() + 0.03
            )),
            new IntakeClearanceInCommand(m_elevatorSubsystem, m_wristSubsystem),
            new ElevatorPositionCommand(m_elevatorSubsystem, MotionConstants.intake.get("elevator").doubleValue())

        );
    }
}

