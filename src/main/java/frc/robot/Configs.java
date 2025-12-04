package frc.robot;

import com.revrobotics.spark.config.SparkMaxConfig;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.util.Units;
import frc.robot.Constants.ClimbConstants;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.ModuleConstants;
import frc.robot.Constants.WristConstants;

public final class Configs {
    public static final class MAXSwerveModule {
        public static final SparkMaxConfig drivingConfig = new SparkMaxConfig();
        public static final SparkMaxConfig turningConfig = new SparkMaxConfig();
        static {
            // Use module constants to calculate conversion factors and feed forward gain.
            double drivingFactor = ModuleConstants.kWheelDiameterMeters * Math.PI
                    / ModuleConstants.kDrivingMotorReduction;
            double turningFactor = 2 * Math.PI;
            double drivingVelocityFeedForward = 1 / ModuleConstants.kDriveWheelFreeSpeedRps;

            drivingConfig
                    .idleMode(IdleMode.kBrake)
                    .smartCurrentLimit(50);
            drivingConfig.encoder
                    .positionConversionFactor(drivingFactor) // meters
                    .velocityConversionFactor(drivingFactor / 60.0); // meters per second
            drivingConfig.closedLoop
                    .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                    // These are example gains you may need to them for your own robot!
                    .pid(0.04, 0, 0)
                    .velocityFF(drivingVelocityFeedForward)
                    .outputRange(-1, 1);

            turningConfig
                    .idleMode(IdleMode.kBrake)
                    .smartCurrentLimit(20);
            turningConfig.absoluteEncoder
                    // Invert the turning encoder, since the output shaft rotates in the opposite
                    // direction of the steering motor in the MAXSwerve Module.
                    .inverted(true)
                    .positionConversionFactor(turningFactor) // radians
                    .velocityConversionFactor(turningFactor / 60.0); // radians per second
            turningConfig.closedLoop
                    .feedbackSensor(FeedbackSensor.kAbsoluteEncoder)
                    // These are example gains you may need to them for your own robot!
                    .pid(1, 0, 0)
                    .outputRange(-1, 1)
                    // Enable PID wrap around for the turning motor. This will allow the PID
                    // controller to go through 0 to get to the setpoint i.e. going from 350 degrees
                    // to 10 degrees will go through 0 rather than the other direction which is a
                    // longer route.
                    .positionWrappingEnabled(true)
                    .positionWrappingInputRange(0, turningFactor);
        }
    }
    
    public static final class ElevatorConfigs{
        public static final SparkMaxConfig elevatorFollowerConfig = new SparkMaxConfig();
        public static final SparkMaxConfig elevatorConfig = new SparkMaxConfig();
        static{
                elevatorConfig
                    .closedLoopRampRate(0.1)
                    .idleMode(IdleMode.kBrake)
                    .smartCurrentLimit(40);
                elevatorConfig.encoder
                    .positionConversionFactor(360)
                    .velocityConversionFactor(1);
                /*
                 * Configure the closed loop controller. We want to make sure we set the
                 * feedback sensor as the primary encoder.
                 */
                elevatorConfig.closedLoop
                    .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                    // Set PID values for position control. We don't need to pass a closed loop
                    // slot, as it will default to slot 0.
                    .p(ElevatorConstants.kP)
                    .i(ElevatorConstants.kI)
                    .d(ElevatorConstants.kD)
                    .outputRange(ElevatorConstants.minOutput, ElevatorConstants.maxOutput);
                elevatorFollowerConfig
                    .apply(elevatorConfig)
                    .follow(ElevatorConstants.elevatorMotorRightCanId, true);
                }               
    }

    public static final class WristConfigs {
        public static final TalonFXConfiguration wristConfig = new TalonFXConfiguration();
        
        // Gear ratio: motor rotations to mechanism rotations
        // TODO: Set actual gear ratio for wrist 
        public static final double WRIST_GEAR_RATIO = 1.0;
        
        static {
            // Motor direction - original REV config had inverted(true)
            wristConfig. MotorOutput.Inverted = InvertedValue. Clockwise_Positive;

            // Neutral/Idle mode - original had IdleMode.kBrake
            wristConfig. MotorOutput.NeutralMode = NeutralModeValue.Brake;

            // Current limits - original had smartCurrentLimit(20)
            wristConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
            wristConfig. CurrentLimits. SupplyCurrentLimit = WristConstants.supplyCurrentLimit;

            // Stator current limit for additional protection
            wristConfig.CurrentLimits.StatorCurrentLimitEnable = true;
            wristConfig.CurrentLimits.StatorCurrentLimit = WristConstants.statorCurrentLimit;

            // Feedback configuration
            // Phoenix 6 uses rotations as the native unit
            // We apply gear ratio so 1 motor rotation = (1/GEAR_RATIO) mechanism rotations
            wristConfig.Feedback. SensorToMechanismRatio = WRIST_GEAR_RATIO;

            // Slot 0 PID gains for position control
            wristConfig.Slot0.kP = WristConstants.kP;
            wristConfig.Slot0.kI = WristConstants.kI;
            wristConfig.Slot0.kD = WristConstants.kD;
            wristConfig.Slot0.kV = WristConstants. kV;
            wristConfig. Slot0.kS = WristConstants. kS;

            // Soft limits in mechanism rotations (degrees converted to rotations)
            wristConfig. SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
            wristConfig.SoftwareLimitSwitch. ForwardSoftLimitThreshold = WristConstants.maxPosition / 360.0;
            wristConfig. SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
            wristConfig. SoftwareLimitSwitch.ReverseSoftLimitThreshold = WristConstants.minPositon / 360.0;

            // Closed-loop ramp rate (optional, smooth motion)
            wristConfig.ClosedLoopRamps.VoltageClosedLoopRampPeriod = 0.1;
        }
    }

    public static final class CoralConfigs {
        public static final SparkMaxConfig coralConfig = new SparkMaxConfig();
        static {
                coralConfig
                        .idleMode(IdleMode.kBrake)
                        .smartCurrentLimit(40);
        }
    }

    public static final class AlgaeConfigs {
        public static final SparkMaxConfig algaeConfig = new SparkMaxConfig();
        static {
                algaeConfig
                        .idleMode(IdleMode.kBrake)
                        .smartCurrentLimit(30);
        }
    }

    public static final class ClimbConfigs {
        public static final SparkMaxConfig climbConfig = new SparkMaxConfig();
        public static final SparkMaxConfig climbFollowerConfig = new SparkMaxConfig();
        static {
                double climbEncoderFactor = 360; //* ClimbConstants.climberGearRatio;
                climbConfig
                    .idleMode(IdleMode.kBrake)

                    .smartCurrentLimit(40);
                climbConfig.absoluteEncoder
                    // Invert the turning encoder, since the output shaft rotates in the opposite
                    // direction of the steering motor in the MAXSwerve Module.
                    // .inverted(true)
                    .positionConversionFactor(climbEncoderFactor) // radians
                    .velocityConversionFactor(climbEncoderFactor / 60.0); // radians per second
                climbConfig.closedLoop
                    .feedbackSensor(FeedbackSensor.kAbsoluteEncoder)
                    // These are example gains you may need to them for your own robot!
                    .pid(1, 0, 0)
                    .outputRange(-1, 1)
                    // Enable PID wrap around for the turning motor. This will allow the PID
                    // controller to go through 0 to get to the setpoint i.e. going from 350 degrees
                    // to 10 degrees will go through 0 rather than the other direction which is a
                    // longer route.
                    .positionWrappingEnabled(true)
                    .positionWrappingInputRange(0, climbEncoderFactor);
                climbConfig.softLimit
                    .forwardSoftLimit((ClimbConstants.maxPosition))
                    .reverseSoftLimit((ClimbConstants.minPosition));
                    // .forwardSoftLimitEnabled(true)
                    // .reverseSoftLimitEnabled(true);
                climbFollowerConfig
                    .apply(climbConfig)
                    .follow(ClimbConstants.climbMotorBottomID, false);
        }
    }
}