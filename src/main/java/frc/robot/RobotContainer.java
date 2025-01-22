// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.OIConstants;
// import frc.robot.commands.ElevatorUpCommand;
// import frc.robot.commands.ElevatorDownCommand;
// import frc.robot.commands.ElevatorEncoderResetCommand;
// import frc.robot.commands.ElevatorPositionCommand;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.DriveSubsystem;
// import frc.robot.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import com.pathplanner.lib.commands.PathPlannerAuto;

/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
  private final DriveSubsystem m_robotDrive = new DriveSubsystem();
  private final ConveyorSubsystem m_ConveyorSubsystem = new ConveyorSubsystem();
  private final ShooterSubsystem m_ShooterSubsystem = new ShooterSubsystem();
  // private final ElevatorSubsystem m_ElevatorSubsystem = new ElevatorSubsystem();
  // The driver's controller
  private final CommandXboxController m_driverController = new CommandXboxController(Constants.OIConstants.kDriverControllerPort);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Configure default commands
    m_robotDrive.setDefaultCommand(
        // The left stick controls translation of the robot.
        // Turning is controlled by the X axis of the right stick.
        new RunCommand(
            () -> m_robotDrive.drive(
                -MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband),
                false),
            m_robotDrive));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its
   * subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling
   * passing it to a
   * {@link JoystickButton}.
   */
  private void configureButtonBindings() {
 //new JoystickButton(m_driverController, Button.kL2.value).whileTrue(new RunCommand(() -> m_robotDrive.setX(),m_robotDrive));
  
    //new JoystickButton(m_driverController, Button.kR1.value).whileTrue(new IntakeCommand(m_ConveyorSubsystem));
    //new JoystickButton(m_driverController,Button.kL1.value).whileTrue(new ShootCommand(m_ShooterSubsystem));
    //new JoystickButton(m_driverController, Button..value).whileTrue(new ElevatorUpCommand(m_ElevatorSubsystem));
    // m_driverController.a().whileTrue(new ElevatorUpCommand(m_ElevatorSubsystem));
    // m_driverController.x().onTrue(new ElevatorPositionCommand(m_ElevatorSubsystem));
    // m_driverController.b().whileTrue(new ElevatorDownCommand(m_ElevatorSubsystem));
    // m_driverController.y().onTrue(new ElevatorEncoderResetCommand(m_ElevatorSubsystem));

}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new PathPlannerAuto(" ");
  }
}