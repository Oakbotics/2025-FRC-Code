package frc.robot.subsystems;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import frc.robot.Constants.CTRESwerveConstants;

public class KrakenSwerveModule {
  private final TalonFX m_driveMotor;
  private final TalonFX m_steerMotor;
  private final CANcoder m_cancoder;

  private final double m_chassisAngularOffset;

  private final VelocityVoltage m_driveRequest = new VelocityVoltage(0).withEnableFOC(true);
  private final PositionVoltage m_steerRequest = new PositionVoltage(0).withEnableFOC(true);
  private final SimpleMotorFeedforward m_driveFeedforward = new SimpleMotorFeedforward(
      CTRESwerveConstants.kDriveKS,
      CTRESwerveConstants.kDriveKV,
      CTRESwerveConstants.kDriveKA);

  private final StatusSignal<Angle> m_drivePosition;
  private final StatusSignal<AngularVelocity> m_driveVelocity;
  private final StatusSignal<Angle> m_steerPosition;

  public KrakenSwerveModule(CTRESwerveConstants.ModuleConfig moduleConfig, double chassisAngularOffset) {
    m_driveMotor = new TalonFX(moduleConfig.driveMotorId, moduleConfig.canBus);
    m_steerMotor = new TalonFX(moduleConfig.steerMotorId, moduleConfig.canBus);
    m_cancoder = new CANcoder(moduleConfig.cancoderId, moduleConfig.canBus);
    m_chassisAngularOffset = chassisAngularOffset;

    configureDriveMotor();
    configureSteerMotor();
    configureCANcoder(moduleConfig.absoluteEncoderOffsetRotations);
    seedSteerFromAbsolute(moduleConfig.absoluteEncoderOffsetRotations);

    m_drivePosition = m_driveMotor.getRotorPosition();
    m_driveVelocity = m_driveMotor.getRotorVelocity();
    m_steerPosition = m_steerMotor.getRotorPosition();
    BaseStatusSignal.setUpdateFrequencyForAll(50, m_drivePosition, m_driveVelocity, m_steerPosition);
  }

  private void configureDriveMotor() {
    TalonFXConfiguration cfg = new TalonFXConfiguration();
    cfg.MotorOutput.NeutralMode = NeutralModeValue.Brake;
    cfg.MotorOutput.Inverted = CTRESwerveConstants.kDriveMotorInverted
        ? InvertedValue.CounterClockwise_Positive
        : InvertedValue.Clockwise_Positive;

    cfg.CurrentLimits.SupplyCurrentLimitEnable = true;
    cfg.CurrentLimits.SupplyCurrentLimit = CTRESwerveConstants.kDriveSupplyCurrentLimit;

    cfg.Slot0.kP = CTRESwerveConstants.kDriveKP; // TODO: tune drive velocity P
    cfg.Slot0.kI = CTRESwerveConstants.kDriveKI; // TODO: tune drive velocity I
    cfg.Slot0.kD = CTRESwerveConstants.kDriveKD; // TODO: tune drive velocity D

    cfg.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = 0.05;
    m_driveMotor.getConfigurator().apply(cfg);
  }

  private void configureSteerMotor() {
    TalonFXConfiguration cfg = new TalonFXConfiguration();
    cfg.MotorOutput.NeutralMode = NeutralModeValue.Brake;
    cfg.MotorOutput.Inverted = CTRESwerveConstants.kSteerMotorInverted
        ? InvertedValue.CounterClockwise_Positive
        : InvertedValue.Clockwise_Positive;

    cfg.CurrentLimits.SupplyCurrentLimitEnable = true;
    cfg.CurrentLimits.SupplyCurrentLimit = CTRESwerveConstants.kSteerSupplyCurrentLimit;

    cfg.Slot0.kP = CTRESwerveConstants.kSteerKP; // TODO: tune steering P
    cfg.Slot0.kI = CTRESwerveConstants.kSteerKI; // TODO: tune steering I
    cfg.Slot0.kD = CTRESwerveConstants.kSteerKD; // TODO: tune steering D

    m_steerMotor.getConfigurator().apply(cfg);
  }

  private void configureCANcoder(double absoluteOffsetRotations) {
    CANcoderConfiguration cfg = new CANcoderConfiguration();
    cfg.MagnetSensor.MagnetOffset = absoluteOffsetRotations; // TODO: set measured offset per module
    cfg.MagnetSensor.SensorDirection = CTRESwerveConstants.kSteerEncoderInverted
        ? com.ctre.phoenix6.signals.SensorDirectionValue.Clockwise_Positive
        : com.ctre.phoenix6.signals.SensorDirectionValue.CounterClockwise_Positive;
    m_cancoder.getConfigurator().apply(cfg);
  }

  private void seedSteerFromAbsolute(double absoluteOffsetRotations) {
    double absoluteRotations = m_cancoder.getAbsolutePosition().refresh().getValueAsDouble();
    double motorRotations = (absoluteRotations - absoluteOffsetRotations) * CTRESwerveConstants.kSteerGearRatio;
    m_steerMotor.setPosition(motorRotations);
  }

  public SwerveModuleState getState() {
    double driveRotationsPerSec = m_driveVelocity.refresh().getValueAsDouble();
    double driveMetersPerSec = (driveRotationsPerSec / CTRESwerveConstants.kDriveGearRatio)
        * CTRESwerveConstants.kWheelCircumferenceMeters;

    double steerRotorPos = m_steerPosition.refresh().getValueAsDouble();
    double moduleRadians = (steerRotorPos / CTRESwerveConstants.kSteerGearRatio) * 2.0 * Math.PI;
    Rotation2d angle = new Rotation2d(moduleRadians - m_chassisAngularOffset);

    return new SwerveModuleState(driveMetersPerSec, angle);
  }

  public SwerveModulePosition getPosition() {
    double driveRotations = m_drivePosition.refresh().getValueAsDouble();
    double driveMeters = (driveRotations / CTRESwerveConstants.kDriveGearRatio)
        * CTRESwerveConstants.kWheelCircumferenceMeters;

    double steerRotorPos = m_steerPosition.refresh().getValueAsDouble();
    double moduleRadians = (steerRotorPos / CTRESwerveConstants.kSteerGearRatio) * 2.0 * Math.PI;
    Rotation2d angle = new Rotation2d(moduleRadians - m_chassisAngularOffset);

    return new SwerveModulePosition(driveMeters, angle);
  }

  public void setDesiredState(SwerveModuleState desiredState) {
    SwerveModuleState correctedDesired = new SwerveModuleState(
        desiredState.speedMetersPerSecond,
        desiredState.angle.plus(Rotation2d.fromRadians(m_chassisAngularOffset)));

    double steerRotorPos = m_steerPosition.refresh().getValueAsDouble();
    double currentModuleRadians = (steerRotorPos / CTRESwerveConstants.kSteerGearRatio) * 2.0 * Math.PI;
    correctedDesired = SwerveModuleState.optimize(correctedDesired, new Rotation2d(currentModuleRadians));

    double wheelRps = correctedDesired.speedMetersPerSecond / CTRESwerveConstants.kWheelCircumferenceMeters;
    double rotorVelocityRps = wheelRps * CTRESwerveConstants.kDriveGearRatio;

    double ffVolts = m_driveFeedforward.calculate(correctedDesired.speedMetersPerSecond);
    double steerSetpointRotorRotations = correctedDesired.angle.getRotations() * CTRESwerveConstants.kSteerGearRatio;

    m_driveMotor.setControl(m_driveRequest.withVelocity(rotorVelocityRps).withFeedForward(ffVolts));
    m_steerMotor.setControl(m_steerRequest.withPosition(steerSetpointRotorRotations));
  }

  public void resetDriveEncoder() {
    m_driveMotor.setPosition(0);
  }

  public double getDriveMetersPerSecond() {
    double driveRotationsPerSec = m_driveVelocity.refresh().getValueAsDouble();
    return (driveRotationsPerSec / CTRESwerveConstants.kDriveGearRatio) * CTRESwerveConstants.kWheelCircumferenceMeters;
  }
}