// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import au.grapplerobotics.ConfigurationFailedException;
import au.grapplerobotics.LaserCan;
import au.grapplerobotics.interfaces.LaserCanInterface.Measurement;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.FunnelConstants;
import frc.robot.Constants.IntakeConstants;

public class FunnelSubsystem extends SubsystemBase {
  LaserCan funnelIntakeSensor;
  Servo funnelServo;
  /** Creates a new ExampleSubsystem. */
  public FunnelSubsystem() {
    funnelServo = new Servo(FunnelConstants.funnelServo);
    funnelIntakeSensor = new LaserCan(IntakeConstants.funnelIntakeSensor);

    try {
      funnelIntakeSensor.setRangingMode(LaserCan.RangingMode.SHORT);
      funnelIntakeSensor.setRegionOfInterest(new LaserCan.RegionOfInterest(8,8,4,4 ));
      funnelIntakeSensor.setTimingBudget(LaserCan.TimingBudget.TIMING_BUDGET_33MS);
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
    Measurement measurment = funnelIntakeSensor.getMeasurement();
    if(measurment != null){
      return measurment.distance_mm;
    }
    return -1;
  }

  /**
   * Uses the sensor to tell if there is a coral in the funnel. If there is a corale we DO NOT want to open the funnel 
   * 
   * @return if there is a coral
   */
   public boolean isCoralInFunnel(){
    return (getSensorValue() < 50);
   }

   /**  
    * Opens the funnel to climb
    *
    */
  public void openFunnel(){
    funnelServo.set(0);
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
