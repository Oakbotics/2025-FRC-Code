package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.Constants.MotionConstants;
import frc.robot.subsystems.ElevatorSubsystem;

public class IntakeCommandGroup extends SequentialCommandGroup {
    public IntakeCommandGroup(ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem){
        addCommands(
            new WristPositionCommand(m_wristSubsystem, 180.0).onlyIf(() -> (m_elevatorSubsystem.getElevatorHeight() > 0.55 || m_elevatorSubsystem.getElevatorHeight() < 0.01)),
            new ElevatorPositionCommand(m_elevatorSubsystem, MotionConstants.intakeClearanceIn.get("elevator").doubleValue()),
            new WristPositionCommand(m_wristSubsystem, MotionConstants.intakeClearanceIn.get("wrist").doubleValue()),
            new ElevatorPositionCommand(m_elevatorSubsystem, MotionConstants.intake.get("elevator").doubleValue())

        );
    }
}

