// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.subsystems;

// import com.revrobotics.spark.SparkMax;
// import com.revrobotics.spark.SparkBase;
// import com.revrobotics.spark.SparkBase.PersistMode;
// import com.revrobotics.spark.SparkLowLevel.MotorType;

// import frc.robot.Configs.GenericNEOConfigs;
// import frc.robot.Constants.EndEffecterConstants;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;

// public class EndEffecterSubsytem extends SubsystemBase {
//   private SparkMax coralMotor;
//   private SparkMax algeaMotor;

//   /** Creates a new ExampleSubsystem. */
//   public EndEffecterSubsytem() {
//     coralMotor = new SparkMax(EndEffecterConstants.coralMotorCANID, MotorType.kBrushless);
//     algeaMotor = new SparkMax(EndEffecterConstants.algeaMotorCANID, MotorType.kBrushless);

//     coralMotor.configure(GenericNEOConfigs.genericNEOConfigs,SparkBase.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
//     algeaMotor.configure(GenericNEOConfigs.genericNEOConfigs,SparkBase.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

//   }

//   /**
//    * Example command factory method.
//    *
//    * @return a command
//    */


//   public void runIntake(double speed){
//     coralMotor.set(speed);
//   }

//   public void runOuttake(double speed){
//     coralMotor.set(-speed);
//   }

//   public void stopIntake(){
//     coralMotor.set(0);
//   }

//   public void stopAlgeaMotor(){
//     algeaMotor.set(0);
//   }

//   public void runAlgeaMotor(double speed){
//     coralMotor.set(speed);
//   }

//   /**
//    * An example method querying a boolean state of the subsystem (for example, a digital sensor).
//    *
//    * @return value of some boolean subsystem state, such as a digital sensor.
//    */
//   public boolean exampleCondition() {
//     // Query some boolean state, such as a digital sensor.
//     return false;
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
