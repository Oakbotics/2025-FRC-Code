// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

// CTRE Phoenix 6 (Kraken) imports
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {
  // Kraken/TalonFX motor controller for coral intake
  private final TalonFX coralMotor;

  // Reusable control object to avoid allocating every call
  private final DutyCycleOut coralDuty = new DutyCycleOut(0.0);

  /** Creates a new IntakeSubsystem using CTRE Kraken (TalonFX). */
  public IntakeSubsystem() {
    // Create the TalonFX with the same CAN ID from Constants
    coralMotor = new TalonFX(IntakeConstants.coralMotorCANID);

    // Basic configuration: neutral mode = brake, and a simple current limit.

  }

  /** Configure coral motor with sensible defaults similar to the old Spark configs. */

  /**
   * Sets speed of coral intake.
   *
   * @param speed duty-cycle [-1.0..1.0], positive direction is as wired on robot
   */
  public void setCoralMotorSpeed(double speed) {
    // Send a duty-cycle control request. Using preallocated DutyCycleOut reduces GC
    coralMotor.setControl(coralDuty.withOutput(speed));
  }

  /** For debugging: read the current motor duty-cycle output. */
  public double getCoralMotorOutput() {
    try {
      return coralMotor.getDutyCycle().getValue();
    } catch (Exception ex) {
      // If signal isn't available in simulation or some build variants, return 0
      return 0.0;
    }
  }

  @Override
  public void periodic() {
    // Show motor output on the dashboard for debugging
    SmartDashboard.putNumber("CoralMotorOutput", getCoralMotorOutput());
  }

  @Override
  public void simulationPeriodic() {
    // Nothing special for simulation in this simplified migration
  }
}