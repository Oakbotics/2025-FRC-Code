// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Set;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.LimelightHelpers;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.FieldConstants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class DriveSubsystem extends SubsystemBase {
  // Create MAXSwerveModules
  private final MAXSwerveModule m_frontLeft = new MAXSwerveModule(
      DriveConstants.kFrontLeftDrivingCanId,
      DriveConstants.kFrontLeftTurningCanId,
      DriveConstants.kFrontLeftChassisAngularOffset);

  private final MAXSwerveModule m_frontRight = new MAXSwerveModule(
      DriveConstants.kFrontRightDrivingCanId,
      DriveConstants.kFrontRightTurningCanId,
      DriveConstants.kFrontRightChassisAngularOffset);

  private final MAXSwerveModule m_rearLeft = new MAXSwerveModule(
      DriveConstants.kRearLeftDrivingCanId,
      DriveConstants.kRearLeftTurningCanId,
      DriveConstants.kBackLeftChassisAngularOffset);

  private final MAXSwerveModule m_rearRight = new MAXSwerveModule(
      DriveConstants.kRearRightDrivingCanId,
      DriveConstants.kRearRightTurningCanId,
      DriveConstants.kBackRightChassisAngularOffset);

      LimeLightSubsystem m_limeLightSubsystem;
      LimelightHelpers limelightHelpers;
  // The gyro sensor
  private final Pigeon2 m_gyro = new Pigeon2(DriveConstants.GyroCanId);
  // Odometry class for tracking robot pose
  SwerveDrivePoseEstimator m_odometry;
  public PathConstraints pathConstraints = new PathConstraints(AutoConstants.kMaxSpeedMetersPerSecond, AutoConstants.kMaxAccelerationMetersPerSecondSquared, AutoConstants.kMaxAngularSpeedRadiansPerSecond, AutoConstants.kMaxAngularSpeedRadiansPerSecondSquared);

  
  /** Creates a new DriveSubsystem. */
  public DriveSubsystem(LimeLightSubsystem limeLightSubsystem) {

   m_limeLightSubsystem = limeLightSubsystem;
   m_odometry = new SwerveDrivePoseEstimator(
    DriveConstants.kDriveKinematics,
    Rotation2d.fromDegrees(m_gyro.getYaw().getValueAsDouble()),
    new SwerveModulePosition[] {
        m_frontLeft.getPosition(),
        m_frontRight.getPosition(),
        m_rearLeft.getPosition(),
        m_rearRight.getPosition()
    },
    new Pose2d(m_limeLightSubsystem.getBotPoseRightLL().getX(), m_limeLightSubsystem.getBotPoseRightLL().getY(), m_gyro.getRotation2d())
    );
    m_gyro.setYaw(0);
    // All other subsystem initialization
    // ...

    // Load the RobotConfig from the GUI settings. You should probably
    // store this in your Constants file
    RobotConfig config = null;
    try{
      config = RobotConfig.fromGUISettings();
    } catch (Exception e) {
      // Handle exception as needed
      e.printStackTrace();
    }

    // Configure AutoBuilder last
    AutoBuilder.configure(
            this::getPose, // Robot pose supplier
            this::resetOdometry, // Method to reset odometry (will be called if your auto has a starting pose)
            this::getChassisSpeeds, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
            (speeds, feedforwards) -> autoDrive(speeds.vxMetersPerSecond, speeds.vyMetersPerSecond, speeds.omegaRadiansPerSecond, false), // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds. Also optionally outputs individual module feedforwards
            new PPHolonomicDriveController( // PPHolonomicController is the built in path following controller for holonomic drive trains
                    new PIDConstants(9.0, 0.0, 0.1), // Translation PID constants 9.0, 0.0, 0.1
                    new PIDConstants(8.0, 0.0, 0.0) // Rotation PID constants
            ),
            config, // The robot configuration
            () -> {
              // Boolean supplier that controls when the path will be mirrored for the red alliance
              // This will flip the path being followed to the red side of the field.
              // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

              // var alliance = DriverStation.getAlliance();
              // if (alliance.isPresent()) {
              //   return alliance.get() == DriverStation.Alliance.Red;
              // }
              return false;
            },
            this // Reference to this subsystem to set requirements
    );
  
    // Usage reporting for MAXSwerve template
    HAL.report(tResourceType.kResourceType_RobotDrive, tInstances.kRobotDriveSwerve_MaxSwerve);
  }
  

  @Override
  public void periodic() {
    // Update the odometry in the periodic block
    m_odometry.update(
        Rotation2d.fromDegrees(m_gyro.getYaw().getValueAsDouble()),
        new SwerveModulePosition[] {
            m_frontLeft.getPosition(),
            m_frontRight.getPosition(),
            m_rearLeft.getPosition(),
            m_rearRight.getPosition()
        });
      LimelightHelpers.SetRobotOrientation("limelight-right",m_odometry.getEstimatedPosition().getRotation().getDegrees(),0,0,0,0,0 );
      LimelightHelpers.SetRobotOrientation("limelight-left",m_odometry.getEstimatedPosition().getRotation().getDegrees(),0,0,0,0,0 );
      
      if(getChassisSpeeds().vxMetersPerSecond < 0.01
        && getChassisSpeeds().vyMetersPerSecond < 0.01
        && getChassisSpeeds().omegaRadiansPerSecond < 0.01)
        limeLightPoseUpdate();
    
    

    SmartDashboard.putNumber("Odometry X", m_odometry.getEstimatedPosition().getX());
    SmartDashboard.putNumber("Odometry Y", m_odometry.getEstimatedPosition().getY());
    SmartDashboard.putNumber("Odometry rot", m_odometry.getEstimatedPosition().getRotation().getDegrees());
  }



   /**
   * Method to drive the robot using joystick info.
   *
   * @param xSpeed        Speed of the robot in the x direction (forward).
   * @param ySpeed        Speed of the robot in the y direction (sideways).
   * @param rot           Angular rate of the robot.
   * @param fieldRelative Whether the provided x and y speeds are relative to the
   *                      field.
   */
  public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
    // Convert the commanded speeds into the correct units for the drivetrain
    double xSpeedDelivered = xSpeed * DriveConstants.kMaxSpeedMetersPerSecond;
    double ySpeedDelivered = ySpeed * DriveConstants.kMaxSpeedMetersPerSecond;
    double rotDelivered = rot * DriveConstants.kMaxAngularSpeed;

    var swerveModuleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(
        fieldRelative
            ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered,
                Rotation2d.fromDegrees(m_gyro.getYaw().getValueAsDouble()))
            : new ChassisSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered));
    SwerveDriveKinematics.desaturateWheelSpeeds(
        swerveModuleStates, DriveConstants.kMaxSpeedMetersPerSecond);
    m_frontLeft.setDesiredState(swerveModuleStates[0]);
    m_frontRight.setDesiredState(swerveModuleStates[1]);
    m_rearLeft.setDesiredState(swerveModuleStates[2]);
    m_rearRight.setDesiredState(swerveModuleStates[3]);
  }

  public void autoDrive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
    // Convert the commanded speeds into the correct units for the drivetrain
    double xSpeedDelivered = xSpeed ;
    double ySpeedDelivered = ySpeed ;
    double rotDelivered = rot ;

    var swerveModuleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(
        fieldRelative
            ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered,
                Rotation2d.fromDegrees(m_gyro.getYaw().getValueAsDouble()))
            : new ChassisSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered));
    SwerveDriveKinematics.desaturateWheelSpeeds(
        swerveModuleStates, AutoConstants.kMaxSpeedMetersPerSecond);
    m_frontLeft.setDesiredState(swerveModuleStates[0]);
    m_frontRight.setDesiredState(swerveModuleStates[1]);
    m_rearLeft.setDesiredState(swerveModuleStates[2]);
    m_rearRight.setDesiredState(swerveModuleStates[3]);
  }

  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPose() {
    return m_odometry.getEstimatedPosition();
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
  public void resetOdometry(Pose2d pose) {
  
    m_odometry.resetPosition(
      Rotation2d.fromDegrees(m_gyro.getYaw().getValueAsDouble()),
      // pose.getRotation(),
      new SwerveModulePosition[] {
          m_frontLeft.getPosition(),
          m_frontRight.getPosition(),
          m_rearLeft.getPosition(),
          m_rearRight.getPosition()
      },
      new Pose2d(pose.getX(), pose.getY(), m_gyro.getRotation2d())
    );
  }


  public ChassisSpeeds getChassisSpeeds(){
    // boolean isFlipped = DriverStation.getAlliance().get() == Alliance.Red;
    // if(isFlipped) {
    //   return ChassisSpeeds.fromFieldRelativeSpeeds(new ChassisSpeeds(
    //       -DriveConstants.kDriveKinematics.toChassisSpeeds(getModuleStates()).vxMetersPerSecond, 
    //       -DriveConstants.kDriveKinematics.toChassisSpeeds(getModuleStates()).vyMetersPerSecond, 
    //       DriveConstants.kDriveKinematics.toChassisSpeeds(getModuleStates()).omegaRadiansPerSecond
    //     ), 
    //     Rotation2d.fromDegrees(m_odometry.getEstimatedPosition().getRotation().getDegrees() + 180));
    // }
    return DriveConstants.kDriveKinematics.toChassisSpeeds(getModuleStates());
  }

  private SwerveModuleState[] getModuleStates() {
    return new SwerveModuleState[] {
            m_frontLeft.getState(),
            m_frontRight.getState(),
            m_rearLeft.getState(),
            m_rearRight.getState()
    };
  }

  public double getEncoderVelocity(){
    return m_frontLeft.getEncoderVelocity();
  }

  /**
   * Sets the wheels into an X formation to prevent movement.
   */
  public void setX() {
    m_frontLeft.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)));
    m_frontRight.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)));
    m_rearLeft.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)));
    m_rearRight.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)));
  }

  /**
   * Sets the swerve ModuleStates.
   *
   * @param desiredStates The desired SwerveModule states.
   */
  public void setModuleStates(SwerveModuleState[] desiredStates) {
    SwerveDriveKinematics.desaturateWheelSpeeds(
        desiredStates, DriveConstants.kMaxSpeedMetersPerSecond);
    m_frontLeft.setDesiredState(desiredStates[0]);
    m_frontRight.setDesiredState(desiredStates[1]);
    m_rearLeft.setDesiredState(desiredStates[2]);
    m_rearRight.setDesiredState(desiredStates[3]);
  }

  /** Resets the drive encoders to currently read a position of 0. */
  public void resetEncoders() {
    m_frontLeft.resetEncoders();
    m_rearLeft.resetEncoders();
    m_frontRight.resetEncoders();
    m_rearRight.resetEncoders();
  }

  /** Sets the heading of the robot. */
  public void setGyro(double angle) {
    m_gyro.setYaw(angle);
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from -180 to 180
   */
  public double getHeading() {
    return m_gyro.getYaw().getValueAsDouble();
  }

  /**
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  public double getTurnRate() {
    return m_gyro.getYaw().getValueAsDouble() * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
  }

  public void resetPoseLL() {

    if(m_limeLightSubsystem.getLeftID() != -1 && m_limeLightSubsystem.getLeftIDCount() > 1)
      resetOdometry(m_limeLightSubsystem.getBotPoseLeftLL());
    else if(m_limeLightSubsystem.getRightID() != -1 && m_limeLightSubsystem.getRightIDCount() > 1)
      resetOdometry(m_limeLightSubsystem.getBotPoseRightLL());
    else if(m_limeLightSubsystem.getTopID() != -1 && m_limeLightSubsystem.getTopIDCount() > 1)
      resetOdometry(m_limeLightSubsystem.getBotPoseTopLL());
    else if(m_limeLightSubsystem.getLeftID() != -1)
      resetOdometry(m_limeLightSubsystem.getBotPoseLeftLL());
    else if(m_limeLightSubsystem.getRightID() != -1)
      resetOdometry(m_limeLightSubsystem.getBotPoseRightLL());
    else if(m_limeLightSubsystem.getTopID() != -1)
      resetOdometry(m_limeLightSubsystem.getBotPoseTopLL());
    }

  public void limeLightPoseUpdate() {
    if(m_limeLightSubsystem.getLeftID() != -1){
      m_odometry.setVisionMeasurementStdDevs(VecBuilder.fill(getPoleDistance(), getPoleDistance() * 1.5, 9999999));
      m_odometry.addVisionMeasurement(new Pose2d(m_limeLightSubsystem.getBotPoseLeftLL().getX(), m_limeLightSubsystem.getBotPoseLeftLL().getY(), m_gyro.getRotation2d()),  m_limeLightSubsystem.getLeftLimelightTime());
    }
    else if(m_limeLightSubsystem.getRightID() != -1){
      m_odometry.setVisionMeasurementStdDevs(VecBuilder.fill(getPoleDistance(), getPoleDistance() * 1.5, 9999999));
      m_odometry.addVisionMeasurement(new Pose2d(m_limeLightSubsystem.getBotPoseRightLL().getX(), m_limeLightSubsystem.getBotPoseRightLL().getY(), m_gyro.getRotation2d()), m_limeLightSubsystem.getRightLimelightTime());
    }
    else if(m_limeLightSubsystem.getTopID() != -1){
      m_odometry.setVisionMeasurementStdDevs(VecBuilder.fill(getPoleDistance(), getPoleDistance() * 1.5, 9999999));
      m_odometry.addVisionMeasurement(new Pose2d(m_limeLightSubsystem.getBotPoseTopLL().getX(), m_limeLightSubsystem.getBotPoseTopLL().getY(), m_gyro.getRotation2d()), m_limeLightSubsystem.getTopLimelightTime());
    }
  }

  public Pose2d getLimeLightPose() {
    if(m_limeLightSubsystem.getTopID() != -1)
      return m_limeLightSubsystem.getBotPoseTopLL();
    else if(m_limeLightSubsystem.getLeftID() != -1)
      return m_limeLightSubsystem.getBotPoseLeftLL();
    else if(m_limeLightSubsystem.getRightID() != -1)
      return m_limeLightSubsystem.getBotPoseRightLL();
    return new Pose2d();
  }

  public void gyroLimelightReset(){
    if(DriverStation.getAlliance().get() == Alliance.Red){
      if(m_limeLightSubsystem.getTopID() != -1)
        setGyro(m_limeLightSubsystem.getBotPoseTopLL().getRotation().getDegrees() + 180);
      else if(m_limeLightSubsystem.getLeftID() != -1)
        setGyro(m_limeLightSubsystem.getBotPoseLeftLL().getRotation().getDegrees() + 180);
      else if(m_limeLightSubsystem.getRightID() != -1)
        setGyro(m_limeLightSubsystem.getBotPoseRightLL().getRotation().getDegrees() + 180); 

    }
    else if(m_limeLightSubsystem.getTopID() != -1)
      setGyro(m_limeLightSubsystem.getBotPoseTopLL().getRotation().getDegrees()); 
    else if(m_limeLightSubsystem.getLeftID() != -1)
      setGyro(m_limeLightSubsystem.getBotPoseLeftLL().getRotation().getDegrees());
    else if(m_limeLightSubsystem.getRightID() != -1)
      setGyro(m_limeLightSubsystem.getBotPoseRightLL().getRotation().getDegrees()); 
  }
  /**
   *  creates defered command from auto build to pose
   * @param pose goal position
   * @return drive command
   */
  public Command findPathToPose(Pose2d pose) {
    return Commands.defer(() -> AutoBuilder.pathfindToPose(pose, pathConstraints), Set.of(this));
  }
  /**
   * Creates a defered command from auto builder to drive to nearest pole
   * @param isLeft left or right pole
   * @return drive command
   */
  public Command findPathToPole(boolean isLeft) {
    return Commands.defer(() -> AutoBuilder.pathfindToPose(getPolePose(isLeft), pathConstraints), Set.of(this));
  }

   /**
   * Creates a defered command from auto builder to drive to nearest face of the reef
   * @return drive command
   */
  public Command findPathToFace() {
    return Commands.defer(() -> AutoBuilder.pathfindToPose(getReefFacePose(), pathConstraints), Set.of(this));
  }

  /**
   * Calculates and returns the nearest poles position from pre defined hashmap relative to odometry pose at time of calling
   * @param isLeft left or right pole 
   * @return pole pose
   */
  public Pose2d getPolePose(boolean isLeft){
    Pose2d nearestPolePose = new Pose2d();
    double nearestPolePoseDistance = Double.MAX_VALUE;
    Pose2d botpose = getPose();
    double[] distances = new double[24];
    if(DriverStation.getAlliance().get() == Alliance.Red){
      for(int i = 6; i < 12; i++){
        Pose2d polePose = FieldConstants.reefPolePositions.get(i)[isLeft ? 0 : 1];
        double distance = Math.sqrt(Math.pow(botpose.getX() - polePose.getX(), 2) + Math.pow(botpose.getY() - polePose.getY(), 2));
        distances[i] = distance;
        if(distance < nearestPolePoseDistance){
          nearestPolePoseDistance = distance;
          nearestPolePose = polePose;
        }
      }
    }
    else if(DriverStation.getAlliance().get() == Alliance.Blue){
      for(int i = 17; i < 23; i++){
        Pose2d polePose = FieldConstants.reefPolePositions.get(i)[isLeft ? 0 : 1];
        double distance = Math.sqrt((botpose.getX() - polePose.getX()) * (botpose.getX() - polePose.getX()) + (botpose.getY() - polePose.getY())*(botpose.getY() - polePose.getY()));
        distances[i] = distance;
        if(distance < nearestPolePoseDistance){
          nearestPolePoseDistance = distance;
          nearestPolePose = polePose;
        }
      }
    }

    
    return nearestPolePose;
  }

  public double getPoleDistance(){
    double nearestPolePoseDistance = Double.MAX_VALUE;
    Pose2d botpose = getPose();
    double[] distances = new double[24];
    if(DriverStation.getAlliance().get() == Alliance.Red){
      for(int i = 6; i < 12; i++){
        Pose2d polePose = FieldConstants.reefPolePositions.get(i)[0];
        double distance = Math.sqrt(Math.pow(botpose.getX() - polePose.getX(), 2) + Math.pow(botpose.getY() - polePose.getY(), 2));
        distances[i] = distance;
        if(distance < nearestPolePoseDistance){
          nearestPolePoseDistance = distance;
         
        }
      }
    }
    else if(DriverStation.getAlliance().get() == Alliance.Blue){
      for(int i = 17; i < 23; i++){
        Pose2d polePose = FieldConstants.reefPolePositions.get(i)[0];
        double distance = Math.sqrt((botpose.getX() - polePose.getX()) * (botpose.getX() - polePose.getX()) + (botpose.getY() - polePose.getY())*(botpose.getY() - polePose.getY()));
        distances[i] = distance;
        if(distance < nearestPolePoseDistance){
          nearestPolePoseDistance = distance;
        }
      }
    }
    return nearestPolePoseDistance;
  }
  @SuppressWarnings("resource")
  public void goToPosePidloop(Pose2d goalPose){
    PIDController xController =new PIDController(DriveConstants.kXP,DriveConstants.kXI, DriveConstants.kXD);
    PIDController yController = new PIDController(DriveConstants.kYP,DriveConstants.kYI, DriveConstants.kYD);
    PIDController rotController = new PIDController(DriveConstants.kRP,DriveConstants.kRI, DriveConstants.kRD);

    rotController.enableContinuousInput(-180, 180);

    // xController.setSetpoint(goalPose.getX());
    // yController.setSetpoint(goalPose.getY());
    // rotController.setSetpoint(goalPose.getRotation().getDegrees());


    xController.setTolerance(0.01);
    yController.setTolerance(0.01);
    rotController.setTolerance(10);
    
    drive(xController.calculate(getPose().getX(), goalPose.getX()),  
          yController.calculate(getPose().getY(), goalPose.getY()), 
          rotController.calculate(getPose().getRotation().getDegrees(), goalPose.getRotation().getDegrees()), false);

    // autoDrive(0,0, 
    //       rotController.calculate(getPose().getRotation().getDegrees(),goalPose.getRotation().getDegrees()), false);

  }

  public Pose2d getReefFacePose(){
    Pose2d nearestFacePose = new Pose2d();
    double nearestFacePoseDistance = Double.MAX_VALUE;
    Pose2d botpose = getPose();
    double[] distances = new double[24];
    if(DriverStation.getAlliance().get() == Alliance.Red){
      for(int i = 6; i < 12; i++){
        Pose2d facePose = FieldConstants.aprilTagPosition.get(i);
        double distance = Math.sqrt(Math.pow(botpose.getX() - facePose.getX(), 2) + Math.pow(botpose.getY() - facePose.getY(), 2));
        distances[i] = distance;
        if(distance < nearestFacePoseDistance){
          nearestFacePoseDistance = distance;
          nearestFacePose = facePose;
        }
      }
    }
    else if(DriverStation.getAlliance().get() == Alliance.Blue){
      for(int i = 17; i < 23; i++){
        Pose2d facePose = FieldConstants.aprilTagPosition.get(i);
        double distance = Math.sqrt((botpose.getX() - facePose.getX()) * (botpose.getX() - facePose.getX()) + (botpose.getY() - facePose.getY())*(botpose.getY() - facePose.getY()));
        distances[i] = distance;
        if(distance < nearestFacePoseDistance){
          nearestFacePoseDistance = distance;
          nearestFacePose = facePose;
        }
      }
    }
    return nearestFacePose;
  }
}