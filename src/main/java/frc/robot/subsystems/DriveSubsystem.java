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
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.CTRESwerveConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.FieldConstants;

public class DriveSubsystem extends SubsystemBase {
  // CTRE Kraken Swerve Modules
  private final KrakenSwerveModule m_frontLeft = new KrakenSwerveModule(
      CTRESwerveConstants.FRONT_LEFT, DriveConstants.kFrontLeftChassisAngularOffset);

  private final KrakenSwerveModule m_frontRight = new KrakenSwerveModule(
      CTRESwerveConstants.FRONT_RIGHT, DriveConstants.kFrontRightChassisAngularOffset);

  private final KrakenSwerveModule m_rearLeft = new KrakenSwerveModule(
      CTRESwerveConstants.BACK_LEFT, DriveConstants.kBackLeftChassisAngularOffset);

  private final KrakenSwerveModule m_rearRight = new KrakenSwerveModule(
      CTRESwerveConstants.BACK_RIGHT, DriveConstants.kBackRightChassisAngularOffset);

  private final LimeLightSubsystem m_limeLightSubsystem;

  // The gyro sensor (CTRE Pigeon2 on CANivore)
  private final Pigeon2 m_gyro = new Pigeon2(DriveConstants.GyroCanId, CTRESwerveConstants.kCANivoreName); // TODO: confirm Pigeon bus

  // Odometry class for tracking robot pose
  private final SwerveDrivePoseEstimator m_odometry;
  public PathConstraints pathConstraints = new PathConstraints(
      AutoConstants.kMaxSpeedMetersPerSecond,
      AutoConstants.kMaxAccelerationMetersPerSecondSquared,
      AutoConstants.kMaxAngularSpeedRadiansPerSecond,
      AutoConstants.kMaxAngularSpeedRadiansPerSecondSquared);

  public DriveSubsystem(LimeLightSubsystem limeLightSubsystem) {
    m_limeLightSubsystem = limeLightSubsystem;
    m_odometry = new SwerveDrivePoseEstimator(
        DriveConstants.kDriveKinematics,
        Rotation2d.fromDegrees(m_gyro.getYaw().getValueAsDouble()),
        getModulePositions(),
        new Pose2d(
            m_limeLightSubsystem.getBotPoseRightLL().getX(),
            m_limeLightSubsystem.getBotPoseRightLL().getY(),
            m_gyro.getRotation2d()));
    m_gyro.setYaw(0);

    RobotConfig config = null;
    try {
      config = RobotConfig.fromGUISettings();
    } catch (Exception e) {
      e.printStackTrace();
    }

    AutoBuilder.configure(
        this::getPose,
        this::resetOdometry,
        this::getChassisSpeeds,
        (speeds, feedforwards) -> autoDrive(
            speeds.vxMetersPerSecond,
            speeds.vyMetersPerSecond,
            speeds.omegaRadiansPerSecond,
            false),
        new PPHolonomicDriveController(
            new PIDConstants(9.0, 0.0, 0.1),
            new PIDConstants(8.0, 0.0, 0.0)),
        config,
        () -> false,
        this);

    HAL.report(tResourceType.kResourceType_RobotDrive, tInstances.kRobotDriveSwerve_MaxSwerve);
  }

  @Override
  public void periodic() {
    m_odometry.update(
        Rotation2d.fromDegrees(m_gyro.getYaw().getValueAsDouble()),
        getModulePositions());
    LimelightHelpers.SetRobotOrientation("limelight-right", m_odometry.getEstimatedPosition().getRotation().getDegrees(), 0, 0, 0, 0, 0);
    LimelightHelpers.SetRobotOrientation("limelight-left", m_odometry.getEstimatedPosition().getRotation().getDegrees(), 0, 0, 0, 0, 0);

    if (Math.abs(getChassisSpeeds().vxMetersPerSecond) < 0.01
        && Math.abs(getChassisSpeeds().vyMetersPerSecond) < 0.01
        && Math.abs(getChassisSpeeds().omegaRadiansPerSecond) < 0.01) {
      limeLightPoseUpdate();
    }

    SmartDashboard.putNumber("Odometry X", m_odometry.getEstimatedPosition().getX());
    SmartDashboard.putNumber("Odometry Y", m_odometry.getEstimatedPosition().getY());
    SmartDashboard.putNumber("Odometry rot", m_odometry.getEstimatedPosition().getRotation().getDegrees());
  }

  public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
    double xSpeedDelivered = xSpeed * DriveConstants.kMaxSpeedMetersPerSecond;
    double ySpeedDelivered = ySpeed * DriveConstants.kMaxSpeedMetersPerSecond;
    double rotDelivered = rot * DriveConstants.kMaxAngularSpeed;

    var swerveModuleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(
        fieldRelative
            ? ChassisSpeeds.fromFieldRelativeSpeeds(
                xSpeedDelivered,
                ySpeedDelivered,
                rotDelivered,
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
    double xSpeedDelivered = xSpeed;
    double ySpeedDelivered = ySpeed;
    double rotDelivered = rot;

    var swerveModuleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(
        fieldRelative
            ? ChassisSpeeds.fromFieldRelativeSpeeds(
                xSpeedDelivered,
                ySpeedDelivered,
                rotDelivered,
                Rotation2d.fromDegrees(m_gyro.getYaw().getValueAsDouble()))
            : new ChassisSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered));
    SwerveDriveKinematics.desaturateWheelSpeeds(
        swerveModuleStates, AutoConstants.kMaxSpeedMetersPerSecond);

    m_frontLeft.setDesiredState(swerveModuleStates[0]);
    m_frontRight.setDesiredState(swerveModuleStates[1]);
    m_rearLeft.setDesiredState(swerveModuleStates[2]);
    m_rearRight.setDesiredState(swerveModuleStates[3]);
  }

  public Pose2d getPose() {
    return m_odometry.getEstimatedPosition();
  }

  public void resetOdometry(Pose2d pose) {
    m_odometry.resetPosition(
        Rotation2d.fromDegrees(m_gyro.getYaw().getValueAsDouble()),
        getModulePositions(),
        new Pose2d(pose.getX(), pose.getY(), m_gyro.getRotation2d()));
  }

  public ChassisSpeeds getChassisSpeeds() {
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

  private SwerveModulePosition[] getModulePositions() {
    return new SwerveModulePosition[] {
        m_frontLeft.getPosition(),
        m_frontRight.getPosition(),
        m_rearLeft.getPosition(),
        m_rearRight.getPosition()
    };
  }

  public double getEncoderVelocity() {
    return m_frontLeft.getDriveMetersPerSecond();
  }

  public void setX() {
    m_frontLeft.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)));
    m_frontRight.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)));
    m_rearLeft.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)));
    m_rearRight.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)));
  }

  public void setModuleStates(SwerveModuleState[] desiredStates) {
    SwerveDriveKinematics.desaturateWheelSpeeds(
        desiredStates, DriveConstants.kMaxSpeedMetersPerSecond);
    m_frontLeft.setDesiredState(desiredStates[0]);
    m_frontRight.setDesiredState(desiredStates[1]);
    m_rearLeft.setDesiredState(desiredStates[2]);
    m_rearRight.setDesiredState(desiredStates[3]);
  }

  public void resetEncoders() {
    m_frontLeft.resetDriveEncoder();
    m_rearLeft.resetDriveEncoder();
    m_frontRight.resetDriveEncoder();
    m_rearRight.resetDriveEncoder();
  }

  public void setGyro(double angle) {
    m_gyro.setYaw(angle);
  }

  public double getHeading() {
    return m_gyro.getYaw().getValueAsDouble();
  }

  public double getTurnRate() {
    return m_gyro.getYaw().getValueAsDouble() * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
  }

  public void resetPoseLL() {
    if (m_limeLightSubsystem.getLeftID() != -1 && m_limeLightSubsystem.getLeftIDCount() > 1)
      resetOdometry(m_limeLightSubsystem.getBotPoseLeftLL());
    else if (m_limeLightSubsystem.getRightID() != -1 && m_limeLightSubsystem.getRightIDCount() > 1)
      resetOdometry(m_limeLightSubsystem.getBotPoseRightLL());
    else if (m_limeLightSubsystem.getTopID() != -1 && m_limeLightSubsystem.getTopIDCount() > 1)
      resetOdometry(m_limeLightSubsystem.getBotPoseTopLL());
    else if (m_limeLightSubsystem.getLeftID() != -1)
      resetOdometry(m_limeLightSubsystem.getBotPoseLeftLL());
    else if (m_limeLightSubsystem.getRightID() != -1)
      resetOdometry(m_limeLightSubsystem.getBotPoseRightLL());
    else if (m_limeLightSubsystem.getTopID() != -1)
      resetOdometry(m_limeLightSubsystem.getBotPoseTopLL());
  }

  public void limeLightPoseUpdate() {
    if (m_limeLightSubsystem.getLeftID() != -1) {
      m_odometry.setVisionMeasurementStdDevs(VecBuilder.fill(getPoleDistance(), getPoleDistance() * 1.5, 9999999));
      m_odometry.addVisionMeasurement(
          new Pose2d(
              m_limeLightSubsystem.getBotPoseLeftLL().getX(),
              m_limeLightSubsystem.getBotPoseLeftLL().getY(),
              m_gyro.getRotation2d()),
          m_limeLightSubsystem.getLeftLimelightTime());
    }
  }

  public Pose2d getLimeLightPose() {
    if (m_limeLightSubsystem.getTopID() != -1)
      return m_limeLightSubsystem.getBotPoseTopLL();
    else if (m_limeLightSubsystem.getLeftID() != -1)
      return m_limeLightSubsystem.getBotPoseLeftLL();
    else if (m_limeLightSubsystem.getRightID() != -1)
      return m_limeLightSubsystem.getBotPoseRightLL();
    return new Pose2d();
  }

  public void gyroLimelightReset() {
    if (DriverStation.getAlliance().get() == Alliance.Red) {
      if (m_limeLightSubsystem.getTopID() != -1)
        setGyro(m_limeLightSubsystem.getBotPoseTopLL().getRotation().getDegrees() + 180);
      else if (m_limeLightSubsystem.getLeftID() != -1)
        setGyro(m_limeLightSubsystem.getBotPoseLeftLL().getRotation().getDegrees() + 180);
      else if (m_limeLightSubsystem.getRightID() != -1)
        setGyro(m_limeLightSubsystem.getBotPoseRightLL().getRotation().getDegrees() + 180);

    } else if (m_limeLightSubsystem.getTopID() != -1)
      setGyro(m_limeLightSubsystem.getBotPoseTopLL().getRotation().getDegrees());
    else if (m_limeLightSubsystem.getLeftID() != -1)
      setGyro(m_limeLightSubsystem.getBotPoseLeftLL().getRotation().getDegrees());
    else if (m_limeLightSubsystem.getRightID() != -1)
      setGyro(m_limeLightSubsystem.getBotPoseRightLL().getRotation().getDegrees());
  }

  public Command findPathToPose(Pose2d pose) {
    return Commands.defer(() -> AutoBuilder.pathfindToPose(pose, pathConstraints), Set.of(this));
  }

  public Command findPathToPose(Pose2d pose, double goalEndVelocity) {
    return Commands.defer(() -> AutoBuilder.pathfindToPose(pose, pathConstraints, goalEndVelocity), Set.of(this));
  }

  public Command findPathToPole(boolean isLeft) {
    return Commands.defer(() -> AutoBuilder.pathfindToPose(getPolePose(isLeft), pathConstraints), Set.of(this));
  }

  public Command findPathToFace() {
    return Commands.defer(() -> AutoBuilder.pathfindToPose(getReefFacePose(), pathConstraints), Set.of(this));
  }

  public Pose2d getPolePose(boolean isLeft){
    Pose2d nearestPolePose = new Pose2d();
    double nearestPolePoseDistance = Double.MAX_VALUE;
    Pose2d botpose = getPose();
    if(DriverStation.getAlliance().get() == Alliance.Red){
      for(int i = 6; i < 12; i++){
        Pose2d polePose = FieldConstants.reefPolePositions.get(i)[isLeft ? 0 : 1];
        double distance = Math.sqrt(Math.pow(botpose.getX() - polePose.getX(), 2) + Math.pow(botpose.getY() - polePose.getY(), 2));
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
    if(DriverStation.getAlliance().get() == Alliance.Red){
      for(int i = 6; i < 12; i++){
        Pose2d polePose = FieldConstants.reefPolePositions.get(i)[0];
        double distance = Math.sqrt(Math.pow(botpose.getX() - polePose.getX(), 2) + Math.pow(botpose.getY() - polePose.getY(), 2));
        if(distance < nearestPolePoseDistance){
          nearestPolePoseDistance = distance;
        }
      }
    }
    else if(DriverStation.getAlliance().get() == Alliance.Blue){
      for(int i = 17; i < 23; i++){
        Pose2d polePose = FieldConstants.reefPolePositions.get(i)[0];
        double distance = Math.sqrt((botpose.getX() - polePose.getX()) * (botpose.getX() - polePose.getX()) + (botpose.getY() - polePose.getY())*(botpose.getY() - polePose.getY()));
        if(distance < nearestPolePoseDistance){
          nearestPolePoseDistance = distance;
        }
      }
    }
    return nearestPolePoseDistance;
  }

  public void goToPosePidloop(Pose2d goalPose){
    PIDController xController =new PIDController(DriveConstants.kXP,DriveConstants.kXI, DriveConstants.kXD);
    PIDController yController = new PIDController(DriveConstants.kYP,DriveConstants.kYI, DriveConstants.kYD);
    PIDController rotController = new PIDController(DriveConstants.kRP,DriveConstants.kRI, DriveConstants.kRD);

    rotController.enableContinuousInput(-180, 180);

    xController.setTolerance(0.01);
    yController.setTolerance(0.01);
    rotController.setTolerance(10);
    
    drive(xController.calculate(getPose().getX(), goalPose.getX()),  
          yController.calculate(getPose().getY(), goalPose.getY()), 
          rotController.calculate(getPose().getRotation().getDegrees(), goalPose.getRotation().getDegrees()), false);
  }

  public Pose2d getReefFacePose(){
    Pose2d nearestFacePose = new Pose2d();
    double nearestFacePoseDistance = Double.MAX_VALUE;
    Pose2d botpose = getPose();
    if(DriverStation.getAlliance().get() == Alliance.Red){
      for(int i = 6; i < 12; i++){
        Pose2d facePose = FieldConstants.aprilTagPosition.get(i);
        double distance = Math.sqrt(Math.pow(botpose.getX() - facePose.getX(), 2) + Math.pow(botpose.getY() - facePose.getY(), 2));
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
        if(distance < nearestFacePoseDistance){
          nearestFacePoseDistance = distance;
          nearestFacePose = facePose;
        }
      }
    }
    return nearestFacePose;
  }
}