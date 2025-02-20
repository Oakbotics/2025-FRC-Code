// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Set;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.ElevatorDownCommand;
import frc.robot.commands.ElevatorPositionCommand;
import frc.robot.commands.ElevatorUpCommand;
import frc.robot.commands.TestAuto;
import frc.robot.commands.WristIntakeCommand;
import frc.robot.commands.WristUpCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.WristSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
  // The robot's subsystems
  private final LimeLightSubsystem m_LimeLightSubsystem = new LimeLightSubsystem();
  private final DriveSubsystem m_robotDrive = new DriveSubsystem(m_LimeLightSubsystem);
  private final ElevatorSubsystem m_elevatorSubsystem = new ElevatorSubsystem();
  private final WristSubsystem m_wristSubsystem = new WristSubsystem();
  // The driver's controller
  CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort);

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
                true),
            m_robotDrive));
  }
  private void configureButtonBindings() {
    // m_driverController.povDown().onTrue(new InstantCommand(() -> m_robotDrive.resetOdometry(m_LimeLightSubsystem.getBotPoseTest())));
    // m_driverController.povUp().onTrue(new InstantCommand(()-> m_robotDrive.zeroHeading()));
    // m_driverController.povLeft().onTrue(new InstantCommand(() -> m_robotDrive.resetOdometry(new Pose2d(0 , 0, Rotation2d.fromDegrees(0)))));

    m_driverController.a().whileTrue(new ElevatorUpCommand(m_elevatorSubsystem));
    // m_driverController.x().onTrue(new ElevatorPositionCommand(m_elevatorSubsystem));
    // m_driverController.y().onTrue(new WristIntakeCommand(m_wristSubsystem));
    m_driverController.b().whileTrue(new ElevatorDownCommand(m_elevatorSubsystem));
    // m_driverController.y().onTrue(new ElevatorEncoderResetCommand(m_ElevatorSubsystem));   
}
  public Command getAutonomousCommand() {
      // return new PathPlannerAuto("3P Middle Top Bottom");
      return new TestAuto(m_robotDrive, m_LimeLightSubsystem);
  }
}