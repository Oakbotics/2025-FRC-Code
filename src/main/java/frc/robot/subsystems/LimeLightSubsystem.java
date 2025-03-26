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
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;

public class LimeLightSubsystem extends SubsystemBase {

  public NetworkTable m_limeLightRightTable; 
   public NetworkTable m_limeLightTopTable;
   public NetworkTable m_limeLightLeftTable; 
   public Pose2d m_closestTagPose;
  // Limelight Left: http://10.37.39.11:5801/
  //Limelight Right: http://10.37.39.12:5801/
  
  private final Field2d m_field = new Field2d();
  /** Creates a new LimeLightSubsystem. */
  public LimeLightSubsystem() {
    m_limeLightRightTable = NetworkTableInstance.getDefault().getTable("limelight-right");
    m_limeLightTopTable = NetworkTableInstance.getDefault().getTable("limelight-top");
    m_limeLightLeftTable = NetworkTableInstance.getDefault().getTable("limelight-left");
    m_limeLightRightTable.getEntry("pipeline").setNumber(0);
    // SmartDashboard.putData("Field", m_field);

  }
  public Pose2d getBotPoseRightLL(){
    double[] botRotArray = m_limeLightRightTable.getEntry("botpose").getDoubleArray(new double[10]); 
    double[] botPoseArray = m_limeLightRightTable.getEntry("botpose_orb").getDoubleArray(new double[10]); 
    Pose2d botPose;
    // SmartDashboard.putNumber("LimelightX", botPoseArray);
    // SmartDashboard.putNumber("LimelightY", ty);
    // SmartDashboard.putNumber("LimelightArea", ta);
    // SmartDashboard.putNumber("AprilTagID", tid);
    // SmartDashboard.putNumber("botpose", tbotpose);
      if(DriverStation.getAlliance().get() == Alliance.Red)  botPose = new Pose2d(botPoseArray[0]+8.7736, botPoseArray[1]+4.0257, Rotation2d.fromDegrees(botRotArray[5] + 180));
      else botPose = new Pose2d(botPoseArray[0] + 8.7736, botPoseArray[1] + 4.0257, Rotation2d.fromDegrees(botRotArray[5]));
      SmartDashboard.putNumber("LimelightX", botPose.getX());
      SmartDashboard.putNumber("LimelightY", botPose.getY());
      // m_field.setRobotPose(botPose);  
      return botPose;

  }

  public Pose2d getBotPoseTopLL(){
    double[] botRotArray = m_limeLightTopTable.getEntry("botpose").getDoubleArray(new double[10]); 
    double[] botPoseArray = m_limeLightTopTable.getEntry("botpose_orb").getDoubleArray(new double[10]); 
    Pose2d botPose;
      if(DriverStation.getAlliance().get() == Alliance.Red)  botPose = new Pose2d(botPoseArray[0]+8.7736, botPoseArray[1]+4.0257, Rotation2d.fromDegrees(botRotArray[5] + 180));
      else botPose = new Pose2d(botPoseArray[0]+8.7736, botPoseArray[1]+4.0257, Rotation2d.fromDegrees(botRotArray[5]));
      m_field.setRobotPose(botPose);  
      return botPose;
  }

  public Pose2d getBotPoseLeftLL(){
    double[] botRotArray = m_limeLightLeftTable.getEntry("botpose").getDoubleArray(new double[10]); 
    double[] botPoseArray = m_limeLightLeftTable.getEntry("botpose_orb").getDoubleArray(new double[10]); 
    Pose2d botPose;
      if(DriverStation.getAlliance().get() == Alliance.Red)  botPose = new Pose2d(botPoseArray[0]+8.7736, botPoseArray[1]+4.0257, Rotation2d.fromDegrees(botRotArray[5] + 180));
      else botPose = new Pose2d(botPoseArray[0]+8.7736, botPoseArray[1]+4.0257, Rotation2d.fromDegrees(botRotArray[5]));
      m_field.setRobotPose(botPose);  
      return botPose;
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

  public int getLeftID(){
    //SmartDashboard.putNumber("networkTableID", m_limeLightTable.getEntry("tid").getValue().getInteger());
    return ((int) m_limeLightLeftTable.getEntry("tid").getDouble(-1));
  }

  public int getTopID(){
    //SmartDashboard.putNumber("networkTableID", m_limeLightTable.getEntry("tid").getValue().getInteger());
    return ((int) m_limeLightTopTable.getEntry("tid").getDouble(-1));
  }

  
  public int getTopIDCount(){
    return ((int) m_limeLightTopTable.getEntry("botpose_orb").getDoubleArray(new double[10])[7]);

  }

  public int getRightIDCount(){
    return ((int) m_limeLightRightTable.getEntry("botpose_orb").getDoubleArray(new double[10])[7]);

  }

  public int getLeftIDCount(){
    return ((int) m_limeLightLeftTable.getEntry("botpose_orb").getDoubleArray(new double[10])[7]);

  }
  public double getRightLimelightTime(){
    return LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight-right").timestampSeconds;
  }
  public double getLeftLimelightTime(){
    return LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight-left").timestampSeconds;
  }
  public double getTopLimelightTime(){
    return LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelihgt-top").timestampSeconds;
  }
  // public Pose2d getClosestTagPose(){
  //   // String allianceTableName = DriverStation.getAlliance() == Alliance.Blue ? "botpose_wpiblue": "botpose_wpired";
  //   if(m_limeLightTable.getEntry("tv").getDouble(0) == 1){
  //     int numTags = m_limeLightTable
  //   }
  // }

  @Override
  public void periodic() {
    getBotPoseRightLL();
    getBotPoseTopLL();
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
