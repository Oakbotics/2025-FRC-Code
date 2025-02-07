// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.subsystems;

// import com.revrobotics.AbsoluteEncoder;
// import com.revrobotics.spark.SparkBase;
// import com.revrobotics.spark.SparkClosedLoopController;
// import com.revrobotics.spark.SparkMax;
// import com.revrobotics.spark.SparkBase.ControlType;
// import com.revrobotics.spark.SparkBase.PersistMode;
// import com.revrobotics.spark.SparkLowLevel.MotorType;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Configs;
// import frc.robot.Constants.ArmConstants;

// public class ArmSubsystem extends SubsystemBase {
//   private final SparkMax armMotor;
//   private final AbsoluteEncoder armEncoder; 
//   private SparkClosedLoopController armPidController;
//   public ArmSubsystem() {
//     armMotor = new SparkMax(ArmConstants.armMotorCANId, MotorType.kBrushless);

//     armMotor.configure(Configs.ArmConfigs.armConfig, SparkBase.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

//     armEncoder = armMotor.getAbsoluteEncoder();
//     armPidController = armMotor.getClosedLoopController();
//   }
  
//   public void armRotateToPosition(double position) {
//     armPidController.setReference(position, ControlType.kPosition);
//   }

//   public void printArmPosition() {
//     double encoderValue = armEncoder.getPosition();

//     SmartDashboard.putNumber("Arm Position", encoderValue);
//   }

//   @Override
//   public void periodic() {
//     // This method will be called once per scheduler run
//   }

//   @Override
//   public void simulationPeriodic() {
//     // This method will be called once per scheduler run during simulation
//   }
// }
