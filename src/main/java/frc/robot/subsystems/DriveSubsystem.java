// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.rmi.registry.RegistryHandler;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import com.ctre.phoenix6.hardware.Pigeon2;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPoint;
import com.pathplanner.lib.path.Waypoint;

import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
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
import edu.wpi.first.wpilibj.Timer;
import frc.robot.LimelightHelpers;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.FieldConstants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.DeferredCommand;
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
                    new PIDConstants(9.0, 0.0, 0.1), // Translation PID constants
                    new PIDConstants(8.0, 0.0, 0.0) // Rotation PID constants
            ),
            config, // The robot configuration
            () -> {
              // Boolean supplier that controls when the path will be mirrored for the red alliance
              // This will flip the path being followed to the red side of the field.
              // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

              var alliance = DriverStation.getAlliance();
              if (alliance.isPresent()) {
                return alliance.get() == DriverStation.Alliance.Red;
              }
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
    //LimelightHelpers.SetRobotOrientation("limelight-top",m_odometry.getEstimatedPosition().getRotation().getDegrees(),0,0,0,0,0 );

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
        swerveModuleStates, DriveConstants.kMaxSpeedMetersPerSecond);
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

  public void resetOdometryLL(Pose2d pose) {
    m_gyro.setYaw(pose.getRotation().getDegrees());

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
    // SwerveModuleState[] swerveModuleStates = {m_frontLeft.getState(), m_frontRight.getState(), m_rearLeft.getState(), m_rearRight.getState()};
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

  /** Zeroes the heading of the robot. */
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

  public void limeLightPoseUpdate() {

    if(m_limeLightSubsystem.getRightID() != -1){}
      resetOdometry(m_limeLightSubsystem.getBotPoseRightLL());
    if(m_limeLightSubsystem.getTopID() != -1)
      resetOdometry(m_limeLightSubsystem.getBotPoseTopLL());
  }

  public Pose2d getLimeLightPose() {
    if(m_limeLightSubsystem.getTopID() != -1)
      return m_limeLightSubsystem.getBotPoseTopLL();
    if(m_limeLightSubsystem.getRightID() != -1)
      return m_limeLightSubsystem.getBotPoseRightLL();
    return new Pose2d();
  }

  public Command findPathToPose(double x, double y, double rotation, boolean isRedAlliance) {
    return findPathToPose(new Pose2d(x, y, Rotation2d.fromDegrees(rotation)), isRedAlliance);
  }

  public Command findPathToPose(Pose2d pose, boolean isRedAlliance) {    
    if(isRedAlliance)
      return AutoBuilder.pathfindToPoseFlipped(pose, pathConstraints);
    else 
      return AutoBuilder.pathfindToPose(pose, pathConstraints);
  }
  public Command findPathToPose(Pose2d pose) {
    // boolean isRedAlliance = DriverStation.getAlliance().get() == Alliance.Red;
    // SmartDashboard.putBoolean("is Red", isRedAlliance);
    // if(isRedAlliance)
    //   return AutoBuilder.pathfindToPoseFlipped(pose, pathConstraints);
    // else 
    return AutoBuilder.pathfindToPose(pose, pathConstraints);
  }

  public PathPlannerPath createPathToPose(Supplier<Pose2d> pose){
    List<Waypoint> waypoints = PathPlannerPath.waypointsFromPoses(
      getPose(),
      pose.get()
    );
    PathPlannerPath path = new PathPlannerPath(waypoints, pathConstraints, null, new GoalEndState(0,pose.get().getRotation()));
    // PathPlannerPath path = PathPlannerPath.fromPathPoints(waypoints, pathConstraints, null);
    return path;
  }

  public Command findPath(Supplier<PathPlannerPath> path) {
    return new DeferredCommand(() -> {
      return AutoBuilder.pathfindThenFollowPath(path.get(), pathConstraints);
    }
    , Set.of(this));
  }

  public Command findPathToPole(Supplier<Pose2d> pose) {
    return findPath(() -> createPathToPose(pose));
  }

    // OLD SOLUTION
  // public Command findPathToPole(boolean isLeft){
  //   limeLightPoseUpdate();
  //   int aprilTagID = -1;
  //   aprilTagID = m_limeLightSubsystem.getID();
  //   Pose2d polePose = getPose();
  //   if (aprilTagID != -1){ 
  //     polePose = FieldConstants.reefPolePositions.get(aprilTagID)[isLeft ? 0 : 1];

  //     SmartDashboard.putNumber("polePathX", polePose.getX());
  //     SmartDashboard.putNumber("polePathY", polePose.getY());
  //     SmartDashboard.putNumber("polePathRotation", polePose.getRotation().getDegrees());
  //     SmartDashboard.putNumber("polePathID", aprilTagID);

  //     return AutoBuilder.pathfindToPose(polePose, pathConstraints);
  //   }
  //   return Commands.none();
  // }

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
    SmartDashboard.putNumber("botPoseX pole", botpose.getX());
    SmartDashboard.putNumber("botPoseY pole", botpose.getY());
    SmartDashboard.putNumber("botPoseRotation pole", botpose.getRotation().getDegrees());
    SmartDashboard.putNumber("polePathX", nearestPolePose.getX());
    SmartDashboard.putNumber("polePathY", nearestPolePose.getY());
    SmartDashboard.putNumber("polePathRotation", nearestPolePose.getRotation().getDegrees());
    SmartDashboard.putNumberArray("distances", distances);
    SmartDashboard.putBoolean("Red Allience?", DriverStation.getAlliance().get() == Alliance.Red);

    return nearestPolePose;
  }
}