// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ConveyorConstants;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;

public class ConveyorSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */

  SparkMax topConveyorMotor;
  SparkMax bottomConveyorMotor;

  public ConveyorSubsystem() {
    topConveyorMotor = new SparkMax(ConveyorConstants.kTopConveyorMotorCANID, MotorType.kBrushless);
    bottomConveyorMotor = new SparkMax(ConveyorConstants.kBottomConveyorMotorCANID, MotorType.kBrushless);
  }

  public void setSpeed(double speed){
    topConveyorMotor.set(-speed);
    bottomConveyorMotor.set(-speed);
  }

  // public boolean getSensorTriggered(){
  //   return getSensorValue() >= 0 && getSensorValue() <= 200;
  // }
  // public boolean getNoteAligned(){
  //   return (getSensorValue() >= 45 && getSensorValue() <= 80 )|| getSensorValue() >= 200;
  // }
  // public boolean getBottomSensorTriggered(){
  //   return (getBottomSensorValue() >= 0 && getBottomSensorValue() <= 70) || (getBottomSensorValue() > 200 && getSensorValue() > 200) ;
  // }
  // public double getSensorValue(){
  //   LaserCan.Measurement measurement = topIntakeSensor.getMeasurement();
  //   if (measurement != null) {
  //       if(measurement.status == LaserCan.LASERCAN_STATUS_VALID_MEASUREMENT){
  //         return measurement.distance_mm;
  //       }
  //   } 
    
  //   return 0.0;
  // }
  // public double getBottomSensorValue(){
  //   LaserCan.Measurement measurement = bottomIntakeSensor.getMeasurement();
  //   if (measurement != null) {
  //       if(measurement.status == LaserCan.LASERCAN_STATUS_VALID_MEASUREMENT){
  //         return measurement.distance_mm;
  //       }
  //   } 
    
  //   return 0.0;
  // }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
