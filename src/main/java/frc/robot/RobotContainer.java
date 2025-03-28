// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.AlgaeKickCommand;
import frc.robot.commands.AlignToReefTagRelative;
import frc.robot.commands.CoralIntakeCommand;
import frc.robot.commands.CoralOuttakeCommand;
import frc.robot.commands.FunnelResetCommand;
import frc.robot.commands.IntakeCommandGroup;
import frc.robot.commands.L2AlgaeCommandGroup;
import frc.robot.commands.L2ScoreCommandGroup;
import frc.robot.commands.L3AlgaeCommandGroup;
import frc.robot.commands.L3ScoreCommandGroup;
import frc.robot.commands.L4ScoreCommandGroup;
import frc.robot.commands.Autos.Middle1Piece;
import frc.robot.commands.ClimberPositionCommand;
import frc.robot.commands.ClimberOutCommand;
import frc.robot.commands.ClimberInCommand;
import frc.robot.commands.LimeLightAutos.Left1PieceLL;
import frc.robot.commands.LimeLightAutos.Left2PieceLL;
import frc.robot.commands.LimeLightAutos.Left3PieceLL;
import frc.robot.commands.LimeLightAutos.Left4PieceLL;
import frc.robot.commands.LimeLightAutos.Middle1PieceLL;
import frc.robot.commands.LimeLightAutos.Right1PieceLL;
import frc.robot.commands.LimeLightAutos.Right2PieceLL;
import frc.robot.commands.LimeLightAutos.Right3PieceLL;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.FunnelSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.subsystems.ClimbSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
  // The robot's subsystems
  private final LimeLightSubsystem m_limeLightSubsystem = new LimeLightSubsystem();
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem(m_limeLightSubsystem);
  private final ElevatorSubsystem m_elevatorSubsystem = new ElevatorSubsystem();
  private final WristSubsystem m_wristSubsystem = new WristSubsystem();
  private final IntakeSubsystem m_intakeSubsytem = new IntakeSubsystem();
  private final FunnelSubsystem m_funnelSubsystem = new FunnelSubsystem();
  private final ClimbSubsystem m_climbSubsystem = new ClimbSubsystem();
  // The drivers controller
  CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
  CommandXboxController m_operatorController = new CommandXboxController(OIConstants.kOperatorControllerPort);
  
  //Auto Chooser
  // SendableChooser<Command> m_autoChooser = new SendableChooser<>();

  public RobotContainer() {
    // AutoChooser Choices
    // m_autoChooser.setDefaultOption("Middle 1 Piece", new Middle1PieceLL(m_driveSubsystem, m_elevatorSubsystem, m_wristSubsystem, m_intakeSubsytem, m_limeLightSubsystem));
    // m_autoChooser.addOption("Left 1 Piece", new Left1PieceLL(m_driveSubsystem, m_elevatorSubsystem, m_wristSubsystem, m_intakeSubsytem, m_limeLightSubsystem));
    // m_autoChooser.addOption("Right 1 Piece", new Right1PieceLL(m_driveSubsystem, m_elevatorSubsystem, m_wristSubsystem, m_intakeSubsytem));
    // m_autoChooser.addOption("Left 2 Piece", new Left2PieceLL(m_driveSubsystem, m_elevatorSubsystem, m_wristSubsystem, m_intakeSubsytem));
    // m_autoChooser.addOption("Right 2 Piece", new Right2PieceLL(m_driveSubsystem, m_elevatorSubsystem, m_wristSubsystem, m_intakeSubsytem));
    // m_autoChooser.addOption("Left 3 Piece", new Left3PieceLL(m_driveSubsystem, m_elevatorSubsystem, m_wristSubsystem, m_intakeSubsytem));
    // m_autoChooser.addOption("Right 3 Piece", new Left3PieceLL(m_driveSubsystem, m_elevatorSubsystem, m_wristSubsystem, m_intakeSubsytem));
    if(DriverStation.getAlliance().get() == Alliance.Red)
    m_driveSubsystem.setDefaultCommand(
      // The left stick controls translation of the robot.
      // Turning is controlled by the X axis of the right stick.
      new RunCommand(
          () -> m_driveSubsystem.drive(
              -MathUtil.applyDeadband(
                  -m_driverController.getLeftY() * (m_driverController.getLeftTriggerAxis() == 1 ? 0.25 : 1),
                  OIConstants.kDriveDeadband),
              -MathUtil.applyDeadband(
                  -m_driverController.getLeftX() * (m_driverController.getLeftTriggerAxis() == 1 ? 0.25 : 1),
                  OIConstants.kDriveDeadband),
              -MathUtil.applyDeadband(
                  m_driverController.getRightX() * (m_driverController.getLeftTriggerAxis() == 1 ? 0.25 : 1),
                  OIConstants.kDriveDeadband),
              true),
          m_driveSubsystem
      )
    );
    // Configure default commands
    else
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
            m_driveSubsystem
        )
      );
      // Configure the button bindings  
      configureButtonBindings();

      //Intake Sensor Auto Intake and Rumble
      new Trigger(() -> m_intakeSubsytem.isCoralOnWrist())
        .onTrue(new RunCommand(() -> m_operatorController.setRumble(RumbleType.kBothRumble, 1.0)).withTimeout(1.0)
        .andThen(new RunCommand(() -> m_operatorController.setRumble(RumbleType.kBothRumble, 0.0)))
      );
      new Trigger(() -> m_intakeSubsytem.isCoralOnWrist())
      .onTrue(new RunCommand(() -> m_driverController.setRumble(RumbleType.kBothRumble, 1.0)).withTimeout(1.0)
      .andThen(new RunCommand(() -> m_driverController.setRumble(RumbleType.kBothRumble, 0.0)))
      );
      new Trigger(() -> (!m_intakeSubsytem.isCoralOnWrist() && m_funnelSubsystem.isCoralInFunnel()))
        .onTrue(new CoralIntakeCommand(m_intakeSubsytem).withTimeout(3.0)
      );
  }
  private void configureButtonBindings() {
    // Driver Controller
    m_driverController.a().onTrue(new L2ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)); // L2 Position
    m_driverController.y().onTrue(new L4ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)); // L4 Position
    m_driverController.x().onTrue(new L3ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)); // L3 Scoring
    m_driverController.b().onTrue(new IntakeCommandGroup(m_elevatorSubsystem, m_wristSubsystem)); // Intake Position

    m_driverController.povUp().onTrue(new InstantCommand(() -> m_driveSubsystem.setGyro(0)));
    m_driverController.povDown().onTrue(new InstantCommand(() -> m_driveSubsystem.gyroLimelightReset()));
    m_driverController.povRight().whileTrue(new CoralIntakeCommand(m_intakeSubsytem));
    m_driverController.povLeft().whileTrue(new InstantCommand(() -> m_driveSubsystem.resetPoseLL()));
    // m_driverController.povRight().onTrue(new InstantCommand(() -> m_driveSubsystem.resetOdometry(new Pose2d(0,0, Rotation2d.fromDegrees(0)))));
    // m_driverController.povLeft().onTrue(new RunCommand(() -> m_driveSubsystem.goToPosePidloop(new Pose2d(1,1,Rotation2d.fromDegrees(180))), m_driveSubsystem).withTimeout(5));

    
    // m_driverController.rightBumper().onTrue(m_driveSubsystem.findPathToPole(false));
    // m_driverController.rightBumper().onTrue(m_driveSubsystem.findPathToPole(false)).onFalse(new RunCommand(() -> m_driveSubsystem.drive(0, 0, 0, false), m_driveSubsystem).withTimeout(0.01));
    // m_driverController.leftBumper().onTrue(m_driveSubsystem.findPathToPole(true)).onFalse(new RunCommand(() -> m_driveSubsystem.drive(0, 0, 0, false), m_driveSubsystem).withTimeout(0.01));

    m_driverController.rightBumper().onTrue(new AlignToReefTagRelative(false, m_driveSubsystem, m_limeLightSubsystem)).onFalse(new RunCommand(() -> m_driveSubsystem.drive(0, 0, 0, false), m_driveSubsystem).withTimeout(0.01));
    m_driverController.leftBumper().onTrue(new AlignToReefTagRelative(true, m_driveSubsystem, m_limeLightSubsystem)).onFalse(new RunCommand(() -> m_driveSubsystem.drive(0, 0, 0, false), m_driveSubsystem).withTimeout(0.01));

    m_driverController.rightTrigger().whileTrue(new CoralOuttakeCommand(m_intakeSubsytem));

    // m_driverController.leftStick()
    // m_driverController.rightStick()

    // Operator Controller
    m_operatorController.rightTrigger().whileTrue(new AlgaeKickCommand(m_intakeSubsytem));
    m_operatorController.leftTrigger().whileTrue(new CoralIntakeCommand(m_intakeSubsytem));

    m_operatorController.a().onTrue(new L2AlgaeCommandGroup(m_elevatorSubsystem, m_wristSubsystem)); // Algae Kick Out Postion L2
    m_operatorController.x().onTrue(new L3AlgaeCommandGroup(m_elevatorSubsystem, m_wristSubsystem)); // Algae Kick Out Postion L3

    m_operatorController.povUp().onTrue(new ClimberPositionCommand(m_climbSubsystem, m_funnelSubsystem));
    m_operatorController.povDown().whileTrue(new ClimberInCommand(m_climbSubsystem));
    m_operatorController.povLeft().whileTrue(new ClimberOutCommand(m_climbSubsystem));
    m_operatorController.povRight().whileTrue(new FunnelResetCommand(m_climbSubsystem, m_funnelSubsystem));
  }

  public Command getAutonomousCommand() {
    // return m_autoChooser.getSelected();
    return new Left3PieceLL(m_driveSubsystem, m_elevatorSubsystem, m_wristSubsystem, m_intakeSubsytem);
    // return new Middle1Piece(m_driveSubsystem, m_elevatorSubsystem, m_wristSubsystem, m_intakeSubsytem);
  }
}