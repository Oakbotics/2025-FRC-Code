// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.FunnelSubsystem;


/** An example command that uses an example subsystem. */
public class ClimberPositionCommand extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final ClimbSubsystem m_climbSubsystem;
  private final FunnelSubsystem m_funnelSubsystem;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ClimberPositionCommand(ClimbSubsystem climbSubsystem, FunnelSubsystem funnelSubsystem) {
    m_climbSubsystem = climbSubsystem;
    m_funnelSubsystem = funnelSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(climbSubsystem, funnelSubsystem);

    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // m_climbSubsystem.rotateToPosition(90); //TEMPORARY
    m_climbSubsystem.setServo(1);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_funnelSubsystem.openFunnel();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
