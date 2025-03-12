// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs.ClimbConfigs;
import frc.robot.Constants.ClimbConstants;

public class ClimbSubsystem extends SubsystemBase {
  Servo climbMotorServoTop;
  Servo climbMotorServoBottom;
  SparkMax climbMotorTop;
  SparkMax climbMotorBottom;
  AbsoluteEncoder climbAbsoluteEncoder;
  SparkClosedLoopController climberPIDController; 

  public ClimbSubsystem() {
    climbMotorServoTop = new Servo(ClimbConstants.climbMotorServoTopChannel);
    climbMotorServoBottom = new Servo(ClimbConstants.climbMotorServoBottomChannel);

    climbMotorTop = new SparkMax(ClimbConstants.climbMotorTopID, MotorType.kBrushless);
    climbMotorBottom = new SparkMax(ClimbConstants.climbMotorBottomID, MotorType.kBrushless);

    climbMotorTop.configure(ClimbConfigs.climbConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    climbMotorBottom.configure(ClimbConfigs.climbFollowerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    climbAbsoluteEncoder = climbMotorTop.getAbsoluteEncoder();
    climberPIDController = climbMotorTop.getClosedLoopController();
  }
  /**
   * climber function rotates to the position it is set to for the robot to pull the cage into itself so it can climb 
   * 
   * @param position position of robot
   */
  public void rotateToPosition(double position){
    climberPIDController.setReference(position, ControlType.kPosition);
  }
  /**
   * Sets the speed of the climb motor
   * 
   * @param speed value of the speed
   */
  public void setSpeed(double speed){
    climbMotorTop.set(speed);
  }
  /**
   * this sets the the climb to be in a position we set
   * 
   * @param position where we want climb?
   */
  public void setServo(double position) {
    climbMotorServoTop.set(position);
    climbMotorServoBottom.set(position);
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
}
