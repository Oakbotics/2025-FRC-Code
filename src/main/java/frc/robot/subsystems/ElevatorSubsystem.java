// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Configs;
import frc.robot.Constants.ElevatorConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.RelativeEncoder;

public class ElevatorSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */

  private final SparkMax m_elevatorMotorLeft;
  private final SparkMax m_elevatorMotorRight;
  private final RelativeEncoder m_elevatorEncoderRight;
  private final SparkClosedLoopController m_elevatorControllerRight;

  public ElevatorSubsystem() {
    m_elevatorMotorLeft = new SparkMax(ElevatorConstants.elevatorMotorLeftCanId, MotorType.kBrushless);
    m_elevatorMotorRight = new SparkMax(ElevatorConstants.elevatorMotorRightCanId, MotorType.kBrushless);
    m_elevatorMotorLeft.configure(Configs.ElevatorConfigs.elevatorFollowerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters); 
    m_elevatorMotorRight.configure(Configs.ElevatorConfigs.elevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_elevatorEncoderRight = m_elevatorMotorRight.getEncoder();
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
