// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import frc.robot.Configs;
import frc.robot.Configs.ElevatorConfigs;
import frc.robot.Constants.ElevatorConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import com.revrobotics.sim.SparkRelativeEncoderSim;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkRelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.util.Units;;

public class ElevatorSubsystem extends SubsystemBase {
  // private final ElevatorConfigs m_ElevatorConfigs;
  private final SparkMax elevatorMotor1;
  private final SparkMax elevatorMotor2;
  private final AbsoluteEncoder m_absoluteEncoder;
  // Creates a PIDController with gains kP, kI, and kD
  private SparkClosedLoopController m_pidController;
  
  /** Creates a new ExampleSubsystem. */
  public ElevatorSubsystem() {

    elevatorMotor1 = new SparkMax(ElevatorConstants.elevatorMotor1CanId, MotorType.kBrushless);
    elevatorMotor2 = new SparkMax(ElevatorConstants.elevatorMotor2CanId, MotorType.kBrushless);
    
    elevatorMotor1.configure(Configs.ElevatorConfigs.elevatorConfig,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);
    
    m_absoluteEncoder = elevatorMotor1.getAbsoluteEncoder();
    m_pidController = elevatorMotor1.getClosedLoopController();

    // m_absoluteEncoder.setPosition(0);
  }
    //probably should make this work based of a height variable in the future
  public void ElevatorRotatePID(double position){
    
    m_pidController.setReference(position / 3, ControlType.kPosition);

  }

  public void restartEncoder(){
    // m_absoluteEncoder.setPosition(0);
    printMotorPosition();
  }

  public void printMotorPosition(){
      SmartDashboard.putNumber("Motor 1", (m_absoluteEncoder.getPosition()));
  }

  public void SetElevatorSpeed(double speed){
    elevatorMotor1.set(speed);
    elevatorMotor2.set(speed);
  }

  public double ElevatorEncoder(){
    return m_absoluteEncoder.getPosition();
  }
  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void resetEncoders() {
    // m_absoluteEncoder.setPosition(0);
  }
}
