// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.AutoAlignCommands.AlignToReefTagRelative;
import frc.robot.commands.Autos.AlgaePieceATR;
import frc.robot.commands.Autos.Left3PieceATR;
import frc.robot.commands.Autos.PieceATR;
import frc.robot.commands.Autos.Right3PieceATR;
import frc.robot.commands.ClimberFunnelCommands.ClimberInCommand;
import frc.robot.commands.ClimberFunnelCommands.ClimberLockCommand;
import frc.robot.commands.ClimberFunnelCommands.ClimberOutCommandGroup;
import frc.robot.commands.ElevatorWristCommands.AlgaeBargeCommandGroup;
import frc.robot.commands.ElevatorWristCommands.AlgaeIntakeCommand;
import frc.robot.commands.ElevatorWristCommands.AlgaeOuttakeCommand;
import frc.robot.commands.ElevatorWristCommands.CoralIntakeCommand;
import frc.robot.commands.ElevatorWristCommands.CoralOuttakeCommand;
import frc.robot.commands.ElevatorWristCommands.IntakeCommandGroup;
import frc.robot.commands.ElevatorWristCommands.L1ScoreCommandGroup;
import frc.robot.commands.ElevatorWristCommands.L2AlgaeCommandGroup;
import frc.robot.commands.ElevatorWristCommands.L2ScoreCommandGroup;
import frc.robot.commands.ElevatorWristCommands.L3AlgaeCommandGroup;
import frc.robot.commands.ElevatorWristCommands.L3ScoreCommandGroup;
import frc.robot.commands.ElevatorWristCommands.L4ScoreCommandGroup;
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
  private DriveSubsystem m_driveSubsystem = new DriveSubsystem(m_limeLightSubsystem);
    private final ElevatorSubsystem m_elevatorSubsystem = new ElevatorSubsystem();
    private final WristSubsystem m_wristSubsystem = new WristSubsystem();
    private final IntakeSubsystem m_intakeSubsytem = new IntakeSubsystem();
    private final FunnelSubsystem m_funnelSubsystem = new FunnelSubsystem();
    private final ClimbSubsystem m_climbSubsystem = new ClimbSubsystem();
    // The drivers controller
    CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
    CommandXboxController m_operatorController = new CommandXboxController(OIConstants.kOperatorControllerPort);
    
    //Auto Chooser
    SendableChooser<Command> m_autoChooser = new SendableChooser<>();
  
    public RobotContainer() {
      switch (Constants.AdvantageScopeConstants.currentMode) {
        case REAL:
          // Real robot, instantiate hardware IO implementations
          m_driveSubsystem  = new DriveSubsystem(new LimeLightSubsystem());
          break;
  
        case SIM:
          // Sim robot, instantiate physics sim IO implementations
          m_driveSubsystem = new DriveSubsystem(new LimeLightSubsystem());
          break;
  
        default:
          // Replayed robot, disable IO implementations
          m_driveSubsystem  = new DriveSubsystem(new LimeLightSubsystem());
        break;
    }
    // AutoChooser Choices
    m_autoChooser.setDefaultOption("1 Piece", new PieceATR(m_driveSubsystem, m_elevatorSubsystem, m_wristSubsystem, m_intakeSubsytem, m_limeLightSubsystem));
    m_autoChooser.addOption("Left 3 Piece", new Left3PieceATR(m_driveSubsystem, m_elevatorSubsystem, m_wristSubsystem, m_intakeSubsytem, m_limeLightSubsystem));
    m_autoChooser.addOption("Right 3 Piece", new Right3PieceATR(m_driveSubsystem, m_elevatorSubsystem, m_wristSubsystem, m_intakeSubsytem, m_limeLightSubsystem));
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
                    m_driverController.getLeftY() * (m_driverController.getLeftTriggerAxis() == 1 ? 0.15 : 1),
                    OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(
                    m_driverController.getLeftX() * (m_driverController.getLeftTriggerAxis() == 1 ? 0.15 : 1),
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

    m_driverController.rightBumper().onTrue(new AlignToReefTagRelative(true, m_driveSubsystem, m_limeLightSubsystem)).onFalse(new RunCommand(() -> m_driveSubsystem.drive(0, 0, 0, false), m_driveSubsystem).withTimeout(0.01));
    m_driverController.leftBumper().onTrue(new AlignToReefTagRelative(false, m_driveSubsystem, m_limeLightSubsystem)).onFalse(new RunCommand(() -> m_driveSubsystem.drive(0, 0, 0, false), m_driveSubsystem).withTimeout(0.01));

    m_driverController.rightTrigger().whileTrue(new CoralOuttakeCommand(m_intakeSubsytem));

    // Operator Controller
    m_operatorController.rightTrigger().whileTrue(new AlgaeIntakeCommand(m_intakeSubsytem));
    m_operatorController.leftTrigger().whileTrue(new CoralIntakeCommand(m_intakeSubsytem));
    m_operatorController.rightBumper().whileTrue(new AlgaeOuttakeCommand(m_intakeSubsytem));

    // m_operatorController.rightBumper().onTrue(new PathToFaceAutoAlignCommandGroup(true, m_driveSubsystem, m_limeLightSubsystem)).onFalse(new RunCommand(() -> m_driveSubsystem.drive(0, 0, 0, false), m_driveSubsystem));
    // m_operatorController.leftBumper().onTrue(new PathToFaceAutoAlignCommandGroup(false, m_driveSubsystem, m_limeLightSubsystem)).onFalse(new RunCommand(() -> m_driveSubsystem.drive(0, 0, 0, false), m_driveSubsystem));


    m_operatorController.a().onTrue(new L2AlgaeCommandGroup(m_elevatorSubsystem, m_wristSubsystem)); // Algae Kick Out Postion L2
    m_operatorController.x().onTrue(new L3AlgaeCommandGroup(m_elevatorSubsystem, m_wristSubsystem)); // Algae Kick Out Postion L3
    
    m_operatorController.b().onTrue(new L1ScoreCommandGroup(m_elevatorSubsystem, m_wristSubsystem)); // Coral L1 Score
    m_operatorController.y().onTrue(new AlgaeBargeCommandGroup(m_elevatorSubsystem, m_wristSubsystem, m_intakeSubsytem));
    
    m_operatorController.povUp().whileTrue(new ClimberOutCommandGroup(m_climbSubsystem));
    m_operatorController.povDown().whileTrue(new ClimberInCommand(m_climbSubsystem));
    m_operatorController.povLeft().onTrue(new RunCommand(() -> m_funnelSubsystem.openFunnel(), m_funnelSubsystem).alongWith(new RunCommand(() -> m_climbSubsystem.setServo(0.7), m_climbSubsystem)).withTimeout(2).andThen(new RunCommand(() -> m_funnelSubsystem.closeFunnel(), m_funnelSubsystem).withTimeout(2)));
    m_operatorController.povRight().onTrue(new ClimberLockCommand(m_climbSubsystem).withTimeout(2));
  }

  public Command getAutonomousCommand() {
    // return m_autoChooser.getSelected();
    return new Left3PieceATR(m_driveSubsystem, m_elevatorSubsystem, m_wristSubsystem, m_intakeSubsytem, m_limeLightSubsystem);
  }

  
}

  