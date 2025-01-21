// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimeLightSubsystem extends SubsystemBase {

  public NetworkTable m_limeLightTable;

  private final Field2d m_Field = new Field2d();
  /** Creates a new LimeLightSubsystem. */
  public LimeLightSubsystem() {
    m_limeLightTable = NetworkTableInstance.getDefault().getTable("limelight");
    m_limeLightTable.getEntry("pipeline").setNumber(0);
  }
  public Pose2d getBotPose(){
    double tx = m_limeLightTable.getEntry("tx").getDouble(0);
    double ty = m_limeLightTable.getEntry("ty").getDouble(0);
    double ta = m_limeLightTable.getEntry("ta").getDouble(0);
    
    SmartDashboard.putNumber("LimelightX", tx);
    SmartDashboard.putNumber("LimelightY", ty);
    SmartDashboard.putNumber("LimelightArea", ta);

    return new Pose2d(tx, ty, Rotation2d.fromDegrees(ta));   
  }
  
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

    getBotPose();

  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
