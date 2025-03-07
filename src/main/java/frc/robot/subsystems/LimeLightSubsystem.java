// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimeLightSubsystem extends SubsystemBase {

  public NetworkTable m_limeLightRightTable; 
   public NetworkTable m_limeLightTopTable;
  public Pose2d m_closestTagPose;
  // Limelight Left: http://10.37.39.11:5801/
  //Limelight Right: 
  
  private final Field2d m_field = new Field2d();
  /** Creates a new LimeLightSubsystem. */
  public LimeLightSubsystem() {
    m_limeLightRightTable = NetworkTableInstance.getDefault().getTable("limelight-right");
    m_limeLightTopTable = NetworkTableInstance.getDefault().getTable("limelight-top");
    m_limeLightRightTable.getEntry("pipeline").setNumber(0);
    SmartDashboard.putData("Field", m_field);

  }
  
  // public Pose2d getBotPose(){
  //   double tx = m_limeLightTable.getEntry("tx").getDouble(0);
  //   double ty = m_limeLightTable.getEntry("ty").getDouble(0);
  //   double ta = m_limeLightTable.getEntry("ta").getDouble(0);
  //   double tid = m_limeLightTable.getEntry("tid").getDouble(0);
  //   double tbotpose = m_limeLightTable.getEntry("botpose[0]").getDouble(0);
    
  //   SmartDashboard.putNumber("LimelightX", tx);
  //   SmartDashboard.putNumber("LimelightY", ty);
  //   SmartDashboard.putNumber("LimelightArea", ta);
  //   SmartDashboard.putNumber("AprilTagID", tid);
  //   SmartDashboard.putNumber("botpose", tbotpose);

  //   return new Pose2d(tx, ty, Rotation2d.fromDegrees(ta));   
  // }
  
  public Pose2d getBotPoseRightLL(){
    double[] botRotArray = m_limeLightRightTable.getEntry("botpose").getDoubleArray(new double[10]); 
    double[] botPoseArray = m_limeLightRightTable.getEntry("botpose_orb").getDoubleArray(new double[10]); 
    Pose2d botPose = new Pose2d();
    // SmartDashboard.putNumber("LimelightX", botPoseArray);
    // SmartDashboard.putNumber("LimelightY", ty);
    // SmartDashboard.putNumber("LimelightArea", ta);
    // SmartDashboard.putNumber("AprilTagID", tid);
    // SmartDashboard.putNumber("botpose", tbotpose);
    if(getRightID() !=-1){
      if(DriverStation.getAlliance().get() == Alliance.Red)  botPose = new Pose2d(botPoseArray[0]+8.7736, botPoseArray[1]+4.0257, Rotation2d.fromDegrees(botRotArray[5] + 180));
      else botPose = new Pose2d(botPoseArray[0] + 8.7736, botPoseArray[1] + 4.0257, Rotation2d.fromDegrees(botRotArray[5]));
      SmartDashboard.putNumber("LimelightX", botPose.getX());
      SmartDashboard.putNumber("LimelightY", botPose.getY());
      // m_field.setRobotPose(botPose);  
      return botPose;
    }
    return botPose;
  }

  public Pose2d getBotPoseTopLL(){
    double[] botRotArray = m_limeLightTopTable.getEntry("botpose").getDoubleArray(new double[10]); 
    double[] botPoseArray = m_limeLightTopTable.getEntry("botpose_orb").getDoubleArray(new double[10]); 
    Pose2d botPose;
    if(getTopID() != -1){
      if(DriverStation.getAlliance().get() == Alliance.Red)  botPose = new Pose2d(botPoseArray[0]+8.7736, botPoseArray[1]+4.0257, Rotation2d.fromDegrees(botRotArray[5] + 180));
      else botPose = new Pose2d(botPoseArray[0]+8.7736, botPoseArray[1]+4.0257, Rotation2d.fromDegrees(botRotArray[5]));
      m_field.setRobotPose(botPose);  
      return botPose;
    }
    return new Pose2d();
  }

  public Pose2d getRobotRelativeTargetPose(){
    double[] targetPoseArray = m_limeLightRightTable.getEntry("targetpose_robotspace").getDoubleArray(new double[10]);
    Pose2d targetPose = new Pose2d(targetPoseArray[2], targetPoseArray[0], Rotation2d.fromDegrees(targetPoseArray[4])); 

    return targetPose;
  }

  public int getRightID(){
    //SmartDashboard.putNumber("networkTableID", m_limeLightTable.getEntry("tid").getValue().getInteger());
    return ((int) m_limeLightRightTable.getEntry("tid").getDouble(-1));
  }

  public int getTopID(){
    //SmartDashboard.putNumber("networkTableID", m_limeLightTable.getEntry("tid").getValue().getInteger());
    return ((int) m_limeLightTopTable.getEntry("tid").getDouble(-1));
  }
  // public Pose2d getClosestTagPose(){
  //   // String allianceTableName = DriverStation.getAlliance() == Alliance.Blue ? "botpose_wpiblue": "botpose_wpired";
  //   if(m_limeLightTable.getEntry("tv").getDouble(0) == 1){
  //     int numTags = m_limeLightTable
  //   }
  // }

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
    //getBotPose();
    getBotPoseRightLL();
    getBotPoseTopLL();
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
