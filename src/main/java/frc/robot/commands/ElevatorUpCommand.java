// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class ElevatorUpCommand extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  // private final ExampleSubsystem m_subsystem;

  private final ElevatorSubsystem m_ElevatorSubsystem;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ElevatorUpCommand(ElevatorSubsystem ElevatorSubsystem) {
    // m_subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(ElevatorSubsystem);
    m_ElevatorSubsystem = ElevatorSubsystem;

  }

  

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_ElevatorSubsystem.setElevatorSpeed(0.1);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_ElevatorSubsystem.setElevatorSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}