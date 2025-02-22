// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.AlgaeKickCommand;
import frc.robot.commands.CoralIntakeCommand;
import frc.robot.commands.CoralOuttakeCommand;
import frc.robot.commands.IntakeCommandGroup;
import frc.robot.commands.L2ScoreCommandGroup;
import frc.robot.commands.L3ScoreCommandGroup;
import frc.robot.commands.L4ScoreCommandGroup;
import frc.robot.commands.TestAuto;
import frc.robot.commands.WristDownCommand;
import frc.robot.commands.WristUpCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsytem;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.WristSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  // The robot's subsystems
  private final LimeLightSubsystem m_LimeLightSubsystem = new LimeLightSubsystem();
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem(m_LimeLightSubsystem);
  private final ElevatorSubsystem m_elevatorSubsystem = new ElevatorSubsystem();
  private final WristSubsystem m_wristSubsystem = new WristSubsystem();
  private final IntakeSubsytem m_intakeSubsytem = new IntakeSubsytem();
  // The driver's controller
  CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
  CommandXboxController m_operatorController = new CommandXboxController(OIConstants.kOperatorControllerPort);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Configure default commands
    // m_driveSubsystem.setDefaultCommand(
    //     // The left stick controls translation of the robot.
    //     // Turning is controlled by the X axis of the right stick.
    //     new RunCommand(
    //         () -> m_driveSubsystem.drive(
    //             -MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband),
    //             -MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband),
    //             -MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband),
    //             true),
    //         m_driveSubsystem));
  }
  private void configureButtonBindings() {
  
    //Driver Controller
    m_driverController.a().onTrue(new L2ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)); // L2 Scoring
    m_driverController.y().onTrue(new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)); //L4 Scoring
    // m_driverController.y().onTrue(new WristUpCommand(m_wristSubsystem)); //L4 Scoring
      m_driverController.x().onTrue(new L3ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)); //L3 Scoring
      // m_driverController.a().onTrue(new WristDownCommand(m_wristSubsystem)); // L2 Scoring
      m_driverController.b().onTrue(new IntakeCommandGroup(m_elevatorSubsystem, m_wristSubsystem)); // Not in use

    m_driverController.povUp().onTrue(new InstantCommand(() -> m_driveSubsystem.zeroHeading()));
      // m_driverController.povLeft() // Not in use
    m_driverController.povDown().onTrue(new InstantCommand(() -> m_driveSubsystem.limeLightPoseUpdate()));
      // m_driverController.povRight() // Not in use

    m_driverController.rightBumper().whileTrue(m_driveSubsystem.findPathToPose(m_driveSubsystem.findPathToPole(false), false));
    m_driverController.leftBumper().whileTrue(m_driveSubsystem.findPathToPose(m_driveSubsystem.findPathToPole(true), false));

    m_driverController.rightTrigger().whileTrue(new CoralOuttakeCommand(m_intakeSubsytem));
    m_driverController.leftTrigger().whileTrue(new CoralIntakeCommand(m_intakeSubsytem));
  
    //Operator Controller
    m_operatorController.rightTrigger().whileTrue(new AlgaeKickCommand(m_intakeSubsytem));

      // m_operatorController.a().onTrue(new ) // Algae Kick Out Postion L2
      // m_operatorController.x().onTrue(new ) // Algae Kick Out Postion L3



}
  public Command getAutonomousCommand() {
      // return new PathPlannerAuto("3P Middle Top Bottom");
      return new TestAuto(m_driveSubsystem, m_LimeLightSubsystem);
  }
  
}