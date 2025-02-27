package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

/** An example command that uses an example subsystem. */
public class ElevatorPositionCommand extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  // private final ExampleSubsystem m_subsystem;

  private final ElevatorSubsystem m_elevatorSubsystem;

  private final double m_position;
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ElevatorPositionCommand(ElevatorSubsystem elevatorSubsystem) {
    // m_subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(elevatorSubsystem);
    m_elevatorSubsystem = elevatorSubsystem;
    m_position = 0.0;
  }

  public ElevatorPositionCommand(ElevatorSubsystem elevatorSubsystem, double position) {
    addRequirements(elevatorSubsystem);
    m_elevatorSubsystem = elevatorSubsystem;
    m_position = position;
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_elevatorSubsystem.elevatorRotatePID(m_position);
  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_elevatorSubsystem.printMotorPosition();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // m_ElevatorSubsystem.SetElevatorSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (m_position - 0.02 < m_elevatorSubsystem.getElevatorHeight()
        && m_position + 0.02 > m_elevatorSubsystem.getElevatorHeight());
  }
}
