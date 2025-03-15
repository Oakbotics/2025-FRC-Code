// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;

import au.grapplerobotics.ConfigurationFailedException;
import au.grapplerobotics.LaserCan;
import au.grapplerobotics.interfaces.LaserCanInterface.Measurement;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs.AlgaeConfigs;
import frc.robot.Configs.CoralConfigs;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {
  SparkMax coralMotor;
  SparkMax algaeMotor;

  LaserCan wristIntakeSensor;
  /** Creates a new ExampleSubsystem. */
  public IntakeSubsystem() {
    coralMotor = new SparkMax(IntakeConstants.coralMotorCANID, MotorType.kBrushless);
    algaeMotor = new SparkMax(IntakeConstants.algaeMotorCANID, MotorType.kBrushless);

    wristIntakeSensor = new LaserCan(IntakeConstants.wristIntakeSensor);

    coralMotor.configure(CoralConfigs.coralConfig,  SparkBase.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    algaeMotor.configure(AlgaeConfigs.algaeConfig,  SparkBase.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    try {
      wristIntakeSensor.setRangingMode(LaserCan.RangingMode.SHORT);
      wristIntakeSensor.setRegionOfInterest(new LaserCan.RegionOfInterest(8,8,4,4 ));
      wristIntakeSensor.setTimingBudget(LaserCan.TimingBudget.TIMING_BUDGET_33MS);
    } catch (ConfigurationFailedException e) {
      e.printStackTrace();
    }
  }
  /**
   * gets the sensor mesurments
   *
   * @return mesurements in millimeters
   */
  public double getSensorValue(){
    Measurement measurment = wristIntakeSensor.getMeasurement();
    if(measurment != null){
      return measurment.distance_mm;
    }
    return -1;
  }
  /**
   * Uses sensor to tell if there is a coral on the wrist of the robot
   * 
   * @return if there is a coral on the wrist
   */
  public boolean isCoralOnWrist(){
    return (getSensorValue() < 5);
  }

  /**
   * Sets the speed of the algae motor to remove algae off the reef
   * 
   * @param speed
   */
  public void setAlgaeMotorSpeed(double speed){
    algaeMotor.set(speed);
  }

  /**
   * Sets speed of coral intake
   * 
   * @param speed speed  of motor
   */
  public void setCoralMotorSpeed(double speed){
    coralMotor.set(speed); 
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
