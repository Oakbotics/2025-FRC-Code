// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OIConstants;
import frc.robot.commands.ElevatorWristCommands.CoralIntakeCommand;
import frc.robot.commands.ElevatorWristCommands.CoralOuttakeCommand;
import frc.robot.commands.ElevatorWristCommands.WristPositionCommand;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.WristSubsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
  private final IntakeSubsystem m_intakeSubsytem = new IntakeSubsystem();
  private final WristSubsystem wrist = new WristSubsystem();


  public RobotContainer() {

      // Configure the button bindings  
      configureButtonBindings();


  }
  private void configureButtonBindings() {
    

    m_driverController.a().whileTrue(new CoralIntakeCommand(m_intakeSubsytem));
    m_driverController.b().whileTrue(new CoralOuttakeCommand(m_intakeSubsytem));
    m_driverController.a().onTrue(new WristPositionCommand(wrist, 30.0));
  }


}