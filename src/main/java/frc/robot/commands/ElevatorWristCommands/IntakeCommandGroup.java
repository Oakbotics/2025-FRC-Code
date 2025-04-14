package frc.robot.commands.ElevatorWristCommands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
// import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.Constants.MotionConstants;
import frc.robot.subsystems.ElevatorSubsystem;

public class IntakeCommandGroup extends SequentialCommandGroup {
    public IntakeCommandGroup(ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem){
        addCommands( 
        //     new ParallelCommandGroup(
        //         new WristPositionCommand(m_wristSubsystem, 180.0),
        //         new ElevatorPositionCommand(m_elevatorSubsystem, MotionConstants.intakeClearanceIn.get("elevator").doubleValue())
        //     ).onlyIf(() -> (
        //         m_elevatorSubsystem.getElevatorHeight() > MotionConstants.l4.get("elevator").doubleValue() - 0.03 
        //         || m_elevatorSubsystem.getElevatorHeight() < MotionConstants.stowed.get("elevator").doubleValue() + 0.03
        // )),
            new ParallelCommandGroup(
                new ElevatorPositionCommand(m_elevatorSubsystem, MotionConstants.intakeClearanceIn.get("elevator").doubleValue()),
                new SequentialCommandGroup(
                    Commands.waitSeconds(0.2),
                    new WristPositionCommand(m_wristSubsystem, MotionConstants.intakeClearanceIn.get("wrist").doubleValue())
                )
            ),

            new ElevatorPositionCommand(m_elevatorSubsystem, MotionConstants.intake.get("elevator").doubleValue())

        );
    }
}

