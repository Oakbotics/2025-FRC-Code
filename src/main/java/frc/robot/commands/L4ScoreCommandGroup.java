package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.MotionConstants;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.WristSubsystem;

public class L4ScoreCommandGroup extends SequentialCommandGroup {
  public L4ScoreCommandGroup(
      ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem) {
    addCommands(
        new ParallelCommandGroup(
                new WristPositionCommand(
                    m_wristSubsystem, MotionConstants.l4.get("wrist").doubleValue()),
                new ElevatorPositionCommand(
                    m_elevatorSubsystem,
                    MotionConstants.intakeClearanceOut.get("elevator").doubleValue()))
            .onlyIf(
                () ->
                    ((m_elevatorSubsystem.getElevatorHeight()
                            < (MotionConstants.intake.get("elevator").doubleValue() + 0.03)
                        && m_elevatorSubsystem.getElevatorHeight()
                            > (MotionConstants.intake.get("elevator").doubleValue() - 0.03)))),
        new ParallelCommandGroup(
            new ElevatorPositionCommand(
                m_elevatorSubsystem, MotionConstants.l4.get("elevator").doubleValue()),
            new WristPositionCommand(
                m_wristSubsystem, MotionConstants.l4.get("wrist").doubleValue())));
  }
}
