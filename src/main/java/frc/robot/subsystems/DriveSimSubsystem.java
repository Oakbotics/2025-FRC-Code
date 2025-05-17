// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// // package frc.robot.subsystems.DriveSubsystem;

// import static frc.robot.subsystems.DriveSubsystem.*;

// import com.ctre.phoenix6.hardware.Pigeon2;
// import com.pathplanner.lib.path.PathConstraints;

// import edu.wpi.first.math.MathUtil;
// import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
// import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotGearing;
// import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotMotor;
// import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotWheelSize;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constants;
// import frc.robot.Constants.AutoConstants;
// import frc.robot.Constants.DriveConstants;
// import frc.robot.LimelightHelpers;


// public class DriveSimSubsystem extends SubsystemBase{
//   private DifferentialDrivetrainSim sim =
//   DifferentialDrivetrainSim.createKitbotSim(
//     KitbotMotor.kDualCIMPerSide, KitbotGearing.k10p71, KitbotWheelSize.kSixInch, null);

//   private double leftAppliedVolts = 0.0;
//   private double rightAppliedVolts = 0.0;
//   private boolean closedLoop = false;
//   private PIDController leftPID = new PIDController(Constants.DriveConstants.simKXP, 0.0, Constants.DriveConstants.simKXD);
//   private PIDController rightPID = new PIDController(Constants.DriveConstants.simKXP, 0.0, Constants.DriveConstants.simKXD);
//   private double leftFFVolts = 0.0;
//   private double rightFFVolts = 0.0;

//   @Override
//   public void updateInputs() {
//   if (closedLoop) {
//     leftAppliedVolts =
//         leftFFVolts + leftPID.calculate(sim.getLeftVelocityMetersPerSecond() / wheelRadiusMeters);
//     rightAppliedVolts =
//         rightFFVolts
//             + rightPID.calculate(sim.getRightVelocityMetersPerSecond() / wheelRadiusMeters);
//   }

//   // Update simulation state
//   sim.setInputs(
//       MathUtil.clamp(leftAppliedVolts, -12.0, 12.0),
//       MathUtil.clamp(rightAppliedVolts, -12.0, 12.0));
//   sim.update(0.02);

//  public DriveSubsystem inputs;
//     inputs.leftPositionRad = sim.getLeftPositionMeters() / discDiameterMeter;
//   inputs.leftVelocityRadPerSec = sim.getLeftVelocityMetersPerSecond() / wheelRadiusMeters;
//   inputs.leftAppliedVolts = leftAppliedVolts;
//   inputs.leftCurrentAmps = new double[] {sim.getLeftCurrentDrawAmps()};

//   inputs.rightPositionRad = sim.getRightPositionMeters() / wheelRadiusMeters;
//   inputs.rightVelocityRadPerSec = sim.getRightVelocityMetersPerSecond() / wheelRadiusMeters;
//   inputs.rightAppliedVolts = rightAppliedVolts;
//   inputs.rightCurrentAmps = new double[] {sim.getRightCurrentDrawAmps()};
//   }

//   @Override
//   public void setVoltage(double leftVolts, double rightVolts) {
//   closedLoop = false;
//   leftAppliedVolts = leftVolts;
//   rightAppliedVolts = rightVolts;
//   }

//   @Override
//     public void setVelocity(
//       double leftRadPerSec, double rightRadPerSec, double leftFFVolts, double rightFFVolts) {
//     closedLoop = true;
//     this.leftFFVolts = leftFFVolts;
//     this.rightFFVolts = rightFFVolts;
//     leftPID.setSetpoint(leftRadPerSec);
//     rightPID.setSetpoint(rightRadPerSec);
//   }
// }