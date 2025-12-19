package frc.robot;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

public final class Constants {
  public static final class FieldConstants {
      public static final Pose2d reefBranchA = new Pose2d(3.246, 4.181,Rotation2d.fromDegrees(0));
      public static final Pose2d reefBranchB = new Pose2d(3.246, 3.875,Rotation2d.fromDegrees(0));
      public static final Pose2d reefBranchC = new Pose2d(3.834, 3.032,Rotation2d.fromDegrees(60));
      public static final Pose2d reefBranchD = new Pose2d(3.246, 2.862,Rotation2d.fromDegrees(60));
      public static final Pose2d reefBranchE = new Pose2d(3.246, 4.181,Rotation2d.fromDegrees(-77.935));
      public static final Pose2d reefBranchF = new Pose2d(3.246, 4.181,Rotation2d.fromDegrees(-77.935));
      public static final Pose2d reefBranchG = new Pose2d(3.246, 4.181,Rotation2d.fromDegrees(-77.935));
      public static final Pose2d reefBranchH = new Pose2d(3.246, 4.181,Rotation2d.fromDegrees(-77.935));
      public static final Pose2d reefBranchI = new Pose2d(3.246, 4.181,Rotation2d.fromDegrees(-77.935));
      public static final Pose2d reefBranchJ = new Pose2d(3.246, 4.181,Rotation2d.fromDegrees(-77.935));
      public static final Pose2d reefBranchK = new Pose2d(3.246, 4.181,Rotation2d.fromDegrees(-77.935));
      public static final Pose2d reefBranchL = new Pose2d(3.246, 4.181,Rotation2d.fromDegrees(-77.935));

      public static final Pose2d[] reefBranches = { reefBranchA, reefBranchB, reefBranchC, reefBranchD, reefBranchE, reefBranchF, reefBranchG, reefBranchH, reefBranchI, reefBranchJ, reefBranchK, reefBranchL};

      public static final double poleToTag = (0.1651);

      public static final Map<Integer, Pose2d[]> reefPolePositions = new HashMap<>();
      static {
        reefPolePositions.put(6, new Pose2d[]{new Pose2d(13.5, 2.8,Rotation2d.fromDegrees(125)), new Pose2d(13.8, 2.9,Rotation2d.fromDegrees(128))});
        reefPolePositions.put(7, new Pose2d[]{new Pose2d(14.36, 3.86,Rotation2d.fromDegrees(180)), new Pose2d(14.3, 4.180,Rotation2d.fromDegrees(180))});
        reefPolePositions.put(8, new Pose2d[]{new Pose2d(13.8, 5.1,Rotation2d.fromDegrees(-115)), new Pose2d(13.6, 5.21,Rotation2d.fromDegrees(-115))});
        reefPolePositions.put(9, new Pose2d[]{new Pose2d(12.23, 5.03,Rotation2d.fromDegrees(-50)), new Pose2d(12.57, 5.23,Rotation2d.fromDegrees(-50))});
        reefPolePositions.put(10, new Pose2d[]{new Pose2d(11.77, 4.2,Rotation2d.fromDegrees(0)), new Pose2d(11.7, 3.8,Rotation2d.fromDegrees(0))});
        reefPolePositions.put(11, new Pose2d[]{new Pose2d(12.2, 3.0,Rotation2d.fromDegrees(60)), new Pose2d(12.5, 2.8,Rotation2d.fromDegrees(60))});
        reefPolePositions.put(17, new Pose2d[]{new Pose2d(3.6, 3.0,Rotation2d.fromDegrees(60)), new Pose2d(3.983, 2.8,Rotation2d.fromDegrees(60))});
        reefPolePositions.put(18, new Pose2d[]{new Pose2d(3.2, 4.2,Rotation2d.fromDegrees(0)), new Pose2d(3.2, 3.9,Rotation2d.fromDegrees(0))});
        reefPolePositions.put(19, new Pose2d[]{new Pose2d(3.9, 5.2,Rotation2d.fromDegrees(-60)), new Pose2d(3.722, 5.050,Rotation2d.fromDegrees(-60))});
        reefPolePositions.put(20, new Pose2d[]{new Pose2d(5.3, 5.033,Rotation2d.fromDegrees(-120)), new Pose2d(5.0, 5.2,Rotation2d.fromDegrees(-120))});
        reefPolePositions.put(21, new Pose2d[]{new Pose2d(5.7, 3.8,Rotation2d.fromDegrees(180)), new Pose2d(5.7, 4.1,Rotation2d.fromDegrees(180))});
        reefPolePositions.put(22, new Pose2d[]{new Pose2d(4.964, 2.837,Rotation2d.fromDegrees(120)), new Pose2d(5.241, 2.984,Rotation2d.fromDegrees(120))});
      }

      public static final Map<Integer, Pose2d[]> coralStationPosition = new HashMap<>();
      static{
        coralStationPosition.put(1, new Pose2d[]{new Pose2d(16.32, 0.681,Rotation2d.fromDegrees(125)), new Pose2d(16.32, 0.681,Rotation2d.fromDegrees(125))});
        coralStationPosition.put(2, new Pose2d[]{new Pose2d(16.946, 7.17,Rotation2d.fromDegrees(-125)), new Pose2d(16.946, 7.17,Rotation2d.fromDegrees(-125))});
        coralStationPosition.put(12, new Pose2d[]{new Pose2d(0.731, 0.778,Rotation2d.fromDegrees(55)), new Pose2d(0.731, 0.778,Rotation2d.fromDegrees(55))});
        coralStationPosition.put(13, new Pose2d[]{new Pose2d(0.975, 7.525,Rotation2d.fromDegrees(-55)), new Pose2d(0.975, 7.525,Rotation2d.fromDegrees(-55))});
      }
      
      public static final Map<Integer, Pose2d> aprilTagPosition = new HashMap<>();
      static{
        aprilTagPosition.put(6, new Pose2d(13.84, 2.61,Rotation2d.fromDegrees(120)));
        aprilTagPosition.put(7, new Pose2d(14.73, 3.98,Rotation2d.fromDegrees(180)));
        aprilTagPosition.put(8, new Pose2d(13.89, 5.41,Rotation2d.fromDegrees(-120)));
        aprilTagPosition.put(9, new Pose2d(12.32, 5.44,Rotation2d.fromDegrees(-60)));
        aprilTagPosition.put(10, new Pose2d(11.42, 4.02,Rotation2d.fromDegrees(0)));
        aprilTagPosition.put(11, new Pose2d(12.19, 2.65,Rotation2d.fromDegrees(60)));

        aprilTagPosition.put(17, new Pose2d(3.66, 2.66,Rotation2d.fromDegrees(60)));
        aprilTagPosition.put(18, new Pose2d(2.87, 4.04,Rotation2d.fromDegrees(0)));
        aprilTagPosition.put(19, new Pose2d(3.70, 5.44,Rotation2d.fromDegrees(-60)));
        aprilTagPosition.put(20, new Pose2d(5.29, 5.37,Rotation2d.fromDegrees(-120)));
        aprilTagPosition.put(21, new Pose2d(6.15, 4.03,Rotation2d.fromDegrees(180)));
        aprilTagPosition.put(22, new Pose2d(5.29, 2.62,Rotation2d.fromDegrees(120)));

        aprilTagPosition.put(14, new Pose2d(7.22, 6.00,Rotation2d.fromDegrees(0)));
        aprilTagPosition.put(5, new Pose2d(10.3, 1.900,Rotation2d.fromDegrees(180)));
      }

      public static final Pose2d topStartingPose = new Pose2d(7.105, 6.171, Rotation2d.fromDegrees(180));
      public static final Pose2d middleStartingPose = new Pose2d(7.181 + 0.10, 4.037, Rotation2d.fromDegrees(180));
      public static final Pose2d bottomStartingPose = new Pose2d(7.085, 1.843, Rotation2d.fromDegrees(180));
  }

  public static final class MotionConstants {
    public static final Map<String, Double> l4 = new HashMap<>();
    static{
      l4.put("elevator", 1.29);
      l4.put("wrist", 180.0);
    }

    public static final Map<String, Double> l3 = new HashMap<>();
    static{
      l3.put("elevator", 0.58);
      l3.put("wrist", 185.0);
    }

    public static final Map<String, Double> l2 = new HashMap<>();
    static{
      l2.put("elevator", 0.14);
      l2.put("wrist", 185.0);
    }

    public static final Map<String, Double> l1 = new HashMap<>();
    static{
      l1.put("elevator", 0.07);
      l1.put("wrist", 150.0);
    }

    public static final Map<String, Double> intake = new HashMap<>();
    static{
      intake.put("elevator", 0.16);
      intake.put("wrist", 22.0);
    }

    public static final Map<String, Double> intakeClearanceOut = new HashMap<>();
    static{
      intakeClearanceOut.put("elevator", 0.31);
      intakeClearanceOut.put("wrist", 180.0);
    }

    public static final Map<String, Double> intakeClearanceIn = new HashMap<>();
    static{
      intakeClearanceIn.put("elevator", 0.31);
      intakeClearanceIn.put("wrist", 10.0);
    }

    public static final Map<String, Double> stowed = new HashMap<>();
    static{
      stowed.put("elevator", 0.0);
      stowed.put("wrist", 0.0);
    }

    public static final Map<String, Double> algaeL2 = new HashMap<>();
    static{
      algaeL2.put("elevator", 0.08);
      algaeL2.put("wrist", 100.0);
    }

    public static final Map<String, Double> algaeL3 = new HashMap<>();
    static{
      algaeL3.put("elevator", 0.56);
      algaeL3.put("wrist", 80.0);
    }
    public static final Map<String, Double> algaeBarge = new HashMap<>();
    static{
      algaeBarge.put("elevator", 1.30);
      algaeBarge.put("wrist", 100.0);
    }
  }

  public static final class ElevatorConstants {
    public static final double discDiameterMeter = Units.inchesToMeters(2.082);
    public static final double discCircumferenceMeter = discDiameterMeter * Math.PI;
    public static final double discGearRatio = 9; 
    public static final int elevatorMotorLeftCanId = 10;
    public static final int elevatorMotorRightCanId = 11;
    
    public static final double kP = 0.0004;
    public static final double kI = 0;
    public static final double kD = 0;

    public static final double minOutput = -1;
    public static final double maxOutput = 1;
  }

  public static final class WristConstants{
    public static final int wristMotorCANId = 12;//Temp
    
    public static final double kP = 0.1;
    public static final double kI = 0;
    public static final double kD = 0;
    public static final double minOutput = -1;
    public static final double maxOutput = 1;
    public static final double velocityFF = 0.5;

    public static final double maxPosition = 225;
    public static final double minPositon = 5;

    // Added feedforward and limits for configs
    public static final double supplyCurrentLimit = 30.0; // TODO: set actual wrist current limit
    public static final double statorCurrentLimit = 30.0; // TODO: set actual wrist stator limit
    public static final double kV = 0.0; // TODO: tune feedforward V
    public static final double kS = 0.0; // TODO: tune feedforward S
  }

  public static final class IntakeConstants {
    public static final int coralMotorCANID = 14;
    public static final int algaeMotorCANID = 13;
    public static final int funnelIntakeSensor = 17;
    public static final int wristIntakeSensor = 18;
  }

  public static final class DriveConstants {
    public static final double kMaxSpeedMetersPerSecond = 5.5;
    public static final double kMaxAngularSpeed =  Math.PI * 2; // radians per second
    
    public static final double kV = 2.76;
    public static final double kS = 0.024;
    public static final double kA = 0.02;

    public static final double kXP = 0.75;
    public static final double kXI = 0.0;
    public static final double kXD = 0.05;

    public static final double kYP = 0.75;
    public static final double kYI = 0.0;
    public static final double kYD = 0.05;

    public static final double kRP = 0.015;
    public static final double kRI = 0.0;
    public static final double kRD = 0.0001;

    public static final double kTrackWidth = Units.inchesToMeters(24.5);
    public static final double kWheelBase = Units.inchesToMeters(24.5);
    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
        new Translation2d(kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

    public static final double kFrontLeftChassisAngularOffset = -Math.PI / 2;
    public static final double kFrontRightChassisAngularOffset = 0;
    public static final double kBackLeftChassisAngularOffset = Math.PI;
    public static final double kBackRightChassisAngularOffset = Math.PI / 2;

    public static final int kFrontLeftDrivingCanId = 7;
    public static final int kRearLeftDrivingCanId = 3;
    public static final int kFrontRightDrivingCanId = 5;
    public static final int kRearRightDrivingCanId = 1;
    
    public static final int kFrontLeftTurningCanId = 8;
    public static final int kRearLeftTurningCanId = 4;
    public static final int kFrontRightTurningCanId = 6;
    public static final int kRearRightTurningCanId = 2;

    public static final boolean kGyroReversed = false;
    public static final int GyroCanId = 9;
  }

  public static final class ModuleConstants {
    public static final int kDrivingMotorPinionTeeth = 14;
    public static final double kDrivingMotorFreeSpeedRps = NeoMotorConstants.kFreeSpeedRpm / 60;
    public static final double kWheelDiameterMeters = 0.0620;
    public static final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;
    public static final double kDrivingMotorReduction = (45.0 * 22) / (kDrivingMotorPinionTeeth * 15);
    public static final double kDriveWheelFreeSpeedRps = (kDrivingMotorFreeSpeedRps * kWheelCircumferenceMeters)
        / kDrivingMotorReduction;
  }

  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 1;
    public static final double kDriveDeadband = 0.03;
  }

  public static final class AutoConstants {
    public static final double kMaxSpeedMetersPerSecond = 4.5;
    public static final double kMaxAccelerationMetersPerSecondSquared = 3;
    public static final double kMaxAngularSpeedRadiansPerSecond =  2 * Math.PI;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
        kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
  }

  public static final class NeoMotorConstants {
    public static final double kFreeSpeedRpm = 5676;
  }
 
  public static final class ClimbConstants {
    public static final int climbMotorServoTopChannel = 1;
    public static final int climbMotorServoBottomChannel = 2;

    public static final double climberGearRatio = (40.0/12.0) * 100.0;

    public static final int climbMotorTopID = 16;
    public static final int climbMotorBottomID = 15;
    
    public static final double minPosition = 5;
    public static final double maxPosition = 230;
  }
  public static final class FunnelConstants {
    public static final int funnelServo = 0;
  }

  /**
   * CTRE / Phoenix6 swerve constants for the Kraken + CANcoder modules.
   * Populate the TODO fields with real hardware values.
   */
  public static final class CTRESwerveConstants {
    public static final String kCANivoreName = "canivore"; // TODO: confirm CANivore name or change to match your bus

    public static final double kWheelDiameterMeters = Units.inchesToMeters(4.0); // TODO: set to your SDS wheel diameter
    public static final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;

    public static final double kDriveGearRatio = 6.75; // TODO: set to your module's drive gear ratio
    public static final double kSteerGearRatio = 12.8; // TODO: set to your module's steer gear ratio

    public static final double kDriveKS = DriveConstants.kS;
    public static final double kDriveKV = DriveConstants.kV;
    public static final double kDriveKA = DriveConstants.kA;

    public static final double kDriveKP = 0.2; // TODO: tune drive velocity P
    public static final double kDriveKI = 0.0; // TODO: tune if needed
    public static final double kDriveKD = 0.0; // TODO: tune if needed

    public static final double kSteerKP = 80.0; // TODO: tune steer position P
    public static final double kSteerKI = 0.0;  // TODO: tune if needed
    public static final double kSteerKD = 1.0;  // TODO: tune steer D

    public static final boolean kDriveMotorInverted = false; // TODO: set per module if required
    public static final boolean kSteerMotorInverted = true;  // TODO: confirm steering inversion
    public static final boolean kSteerEncoderInverted = false; // TODO: confirm CANcoder inversion

    public static final double kDriveSupplyCurrentLimit = 50.0; // TODO: confirm current limit
    public static final double kSteerSupplyCurrentLimit = 30.0; // TODO: confirm current limit

    public static final class ModuleConfig {
      public final int driveMotorId;
      public final int steerMotorId;
      public final int cancoderId;
      public final double absoluteEncoderOffsetRotations;
      public final String canBus;

      public ModuleConfig(int driveMotorId, int steerMotorId, int cancoderId, double absoluteEncoderOffsetRotations, String canBus) {
        this.driveMotorId = driveMotorId;
        this.steerMotorId = steerMotorId;
        this.cancoderId = cancoderId;
        this.absoluteEncoderOffsetRotations = absoluteEncoderOffsetRotations;
        this.canBus = canBus;
      }
    }

    // TODO: replace CAN IDs and absolute offsets with real values for each module
    public static final ModuleConfig FRONT_LEFT  = new ModuleConfig(21, 22, 31, 0.0, kCANivoreName); // TODO: set IDs/offset
    public static final ModuleConfig FRONT_RIGHT = new ModuleConfig(23, 24, 32, 0.0, kCANivoreName); // TODO: set IDs/offset
    public static final ModuleConfig BACK_LEFT   = new ModuleConfig(25, 26, 33, 0.0, kCANivoreName); // TODO: set IDs/offset
    public static final ModuleConfig BACK_RIGHT  = new ModuleConfig(27, 28, 34, 0.0, kCANivoreName); // TODO: set IDs/offset
  }
}