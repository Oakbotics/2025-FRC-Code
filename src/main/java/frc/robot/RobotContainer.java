// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.HashSet;
import java.util.Set;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.AlgaeKickCommand;
import frc.robot.commands.CoralIntakeCommand;
import frc.robot.commands.PIDTuningHelperCommands.*;
import frc.robot.commands.CoralOuttakeCommand;
import frc.robot.commands.GoToPoseCommand;
import frc.robot.commands.IntakeCommandGroup;
import frc.robot.commands.L2AlgaeCommandGroup;
import frc.robot.commands.L2ScoreCommandGroup;
import frc.robot.commands.L3AlgaeCommandGroup;
import frc.robot.commands.L3ScoreCommandGroup;
import frc.robot.commands.L4ScoreCommandGroup;
import frc.robot.commands.TestAuto;
import frc.robot.commands.WristDownCommand;
import frc.robot.commands.WristUpCommand;
import frc.robot.commands.Autos.Bottom1Piece;
import frc.robot.commands.Autos.Middle1Piece;
import frc.robot.commands.Autos.Middle1PieceLL;
import frc.robot.commands.Autos.Middle3Piece;
import frc.robot.commands.Autos.Middle3PieceLL;
import frc.robot.commands.Autos.Top1Piece;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.WristSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.DeferredCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  // The robot's subsystems
  private final LimeLightSubsystem m_LimeLightSubsystem = new LimeLightSubsystem();
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem(m_LimeLightSubsystem);
  private final ElevatorSubsystem m_elevatorSubsystem = new ElevatorSubsystem();
  private final WristSubsystem m_wristSubsystem = new WristSubsystem();
  private final IntakeSubsystem m_intakeSubsytem = new IntakeSubsystem();
  // The driver's controller
  CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
  CommandXboxController m_operatorController = new CommandXboxController(OIConstants.kOperatorControllerPort);
  Set<Subsystem> deferredSubsystemsSet = new HashSet<Subsystem>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    deferredSubsystemsSet.add(m_driveSubsystem);

    // Configure the button bindings

    // Configure default commands
    m_driveSubsystem.setDefaultCommand(
        // The left stick controls translation of the robot.
        // Turning is controlled by the X axis of the right stick.
        new RunCommand(
            () -> m_driveSubsystem.drive(
                -MathUtil.applyDeadband(
                    m_driverController.getLeftY() * (m_driverController.getLeftTriggerAxis() == 1 ? 0.25 : 1),
                    OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(
                    m_driverController.getLeftX() * (m_driverController.getLeftTriggerAxis() == 1 ? 0.25 : 1),
                    OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(
                    m_driverController.getRightX() * (m_driverController.getLeftTriggerAxis() == 1 ? 0.25 : 1),
                    OIConstants.kDriveDeadband),
                true),
            m_driveSubsystem));
            
      configureButtonBindings();

  }

  private void configureButtonBindings() {

    // Driver Controller
    m_driverController.a().onTrue(new L2ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)); // L2 Scoring
    m_driverController.y().onTrue(new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)); // L4 Scoring
    // m_driverController.y().onTrue(new WristUpCommand(m_wristSubsystem)); //L4
    // Scoring
    m_driverController.x().onTrue(new L3ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)); // L3 Scoring
    // m_driverController.a().onTrue(new WristDownCommand(m_wristSubsystem)); // L2
    // Scoring
    m_driverController.b().onTrue(new IntakeCommandGroup(m_elevatorSubsystem, m_wristSubsystem)); // Not in use

    m_driverController.povUp().onTrue(new InstantCommand(() -> m_driveSubsystem.setGyro(0)));
    m_driverController.povLeft().onTrue((new InstantCommand(
        () -> m_driveSubsystem.gyroLimelightReset()))); // Not
                                                        // in
                                                        // use
    m_driverController.povDown().onTrue(new InstantCommand(() -> m_driveSubsystem.limeLightPoseUpdate()));
    m_driverController.povRight().whileTrue(new CoralIntakeCommand(m_intakeSubsytem));
    // m_driverController.povRight().onTrue(new InstantCommand(() ->
    // m_driveSubsystem.setGyro(180))); // Not in use

    // m_driverController.rightBumper().whileTrue(new
    // GoToPoseCommand(m_driveSubsystem, false));
    // m_driverController.leftBumper().whileTrue(new
    // GoToPoseCommand(m_driveSubsystem, true));

    // ------------TO PLAY AROUND WITH AFTER MATCH:------------
    m_driverController.rightBumper().onTrue(
        m_driveSubsystem.findPathToPole(false));
    m_driverController.leftBumper().onTrue(
        m_driveSubsystem.findPathToPole(true));

    // m_driverController.rightBumper().onTrue(new DeferredCommand(() -> 
    //   m_driveSubsystem.findPathToPole(m_driveSubsystem.getPolePose(false)),
    //   Set.of(m_driveSubsystem)));
    // m_driverController.leftBumper().onTrue(new DeferredCommand(() -> 
    //   m_driveSubsystem.findPathToPole(m_driveSubsystem.getPolePose(true)),
    //   Set.of(m_driveSubsystem)));

    // m_driverController.leftBumper().onTrue(m_driveSubsystem.findPathToPole(() -> m_driveSubsystem.getPolePose(true)));
    // m_driverController.rightBumper().onTrue(m_driveSubsystem.findPathToPole(() -> m_driveSubsystem.getPolePose(false)));
    // m_driverController.leftBumper().whileTrue(new DeferredCommand(() ->
    // m_driveSubsystem.findPathToPose(m_driveSubsystem.getPolePose(true)),
    // deferredSubsystemsSet));

    m_driverController.rightTrigger().whileTrue(new CoralOuttakeCommand(m_intakeSubsytem));
    // m_driverController.leftTrigger().whileTrue(new
    // CoralIntakeCommand(m_intakeSubsytem));

    // Operator Controller
    m_operatorController.rightTrigger().whileTrue(new AlgaeKickCommand(m_intakeSubsytem));
    m_operatorController.leftTrigger().whileTrue(new CoralIntakeCommand(m_intakeSubsytem));

    m_operatorController.a().onTrue(new L2AlgaeCommandGroup(m_elevatorSubsystem, m_wristSubsystem)); // Algae Kick Out
                                                                                                     // Postion L2
    m_operatorController.x().onTrue(new L3AlgaeCommandGroup(m_elevatorSubsystem, m_wristSubsystem)); // Algae Kick Out
                                                                                                     // Postion L3

  }

  public Command getAutonomousCommand() {
    // return new PathPlannerAuto("3P Middle Top Bottom");
    // return new Top1Piece(m_driveSubsystem, m_elevatorSubsystem, m_wristSubsystem,
    // m_intakeSubsytem);
    return new Middle1PieceLL(m_driveSubsystem, m_elevatorSubsystem, m_wristSubsystem, m_intakeSubsytem, m_LimeLightSubsystem);
    // return new Bottom1Piece(m_driveSubsystem, m_elevatorSubsystem,
    // m_wristSubsystem, m_intakeSubsytem);
  }
}