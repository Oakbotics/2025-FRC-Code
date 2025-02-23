// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class ConveyorConstants {
    // public static final int kTopConveyorMotorCANID = 14;
    // public static final int kBottomConveyorMotorCANID = 15;

    // public statics final int kRightShooterMotorCANID = 20;
    // public static final int kLeftShooterMotorCANID = 21;

    // public static final double revvedShooterRPM = 800;

    // public static final int kTopIntakeSensorCANID = 5;
    // public static final int kBottomIntakeSensorCANID = 25;
  }



  public static final class FieldConstants {
      public static final Pose2d reefBranchA = new Pose2d(3.246, 4.181,Rotation2d.fromDegrees(0));
      public static final Pose2d reefBranchB = new Pose2d(3.246, 3.875,Rotation2d.fromDegrees(0));
      public static final Pose2d reefBranchC = new Pose2d(3.834, 3.032,Rotation2d.fromDegrees(60));
      public static final Pose2d reefBranchD = new Pose2d(3.246, 2.862,Rotation2d.fromDegrees(60));//stopped here
      public static final Pose2d reefBranchE = new Pose2d(3.246, 4.181,Rotation2d.fromDegrees(-77.935));
      public static final Pose2d reefBranchF = new Pose2d(3.246, 4.181,Rotation2d.fromDegrees(-77.935));
      public static final Pose2d reefBranchG = new Pose2d(3.246, 4.181,Rotation2d.fromDegrees(-77.935));
      public static final Pose2d reefBranchH = new Pose2d(3.246, 4.181,Rotation2d.fromDegrees(-77.935));
      public static final Pose2d reefBranchI = new Pose2d(3.246, 4.181,Rotation2d.fromDegrees(-77.935));
      public static final Pose2d reefBranchJ = new Pose2d(3.246, 4.181,Rotation2d.fromDegrees(-77.935));
      public static final Pose2d reefBranchK = new Pose2d(3.246, 4.181,Rotation2d.fromDegrees(-77.935));
      public static final Pose2d reefBranchL = new Pose2d(3.246, 4.181,Rotation2d.fromDegrees(-77.935));

      public static final Pose2d[] reefBranches = { reefBranchA, reefBranchB, reefBranchC, reefBranchD, reefBranchE, reefBranchF, reefBranchG, reefBranchH, reefBranchI, reefBranchJ, reefBranchK, reefBranchL};

      // public static final double poleToTag = (0.1651);
      // public static final HashMap<Integer, Pose2d[]> reefPolePositions = new HashMap();

      // static { //branches are ordered left to right
      //   reefPolePositions.put(6, new Pose2d[]{new Pose2d(13.524, 2.830,Rotation2d.fromDegrees(117.987)), new Pose2d(13.833, 3.003,Rotation2d.fromDegrees(117.987))});
      //   reefPolePositions.put(7, new Pose2d[]{new Pose2d(14.324, 3.859,Rotation2d.fromDegrees(-178.596)), new Pose2d(14.337, 4.190,Rotation2d.fromDegrees(-178.596))});
      //   reefPolePositions.put(8, new Pose2d[]{new Pose2d(13.884, 5.015,Rotation2d.fromDegrees(-127.926)), new Pose2d(13.567, 5.216,Rotation2d.fromDegrees(-121.350))});
      //   reefPolePositions.put(9, new Pose2d[]{new Pose2d(12.534, 5.216,Rotation2d.fromDegrees(-56.014)), new Pose2d(12.265, 5.041,Rotation2d.fromDegrees(-56.014))});
      //   reefPolePositions.put(10, new Pose2d[]{new Pose2d(11.771, 4.165,Rotation2d.fromDegrees(0)), new Pose2d(11.773, 3.859,Rotation2d.fromDegrees(0))});
      //   reefPolePositions.put(11, new Pose2d[]{new Pose2d(12.223, 3.049,Rotation2d.fromDegrees(54.545)), new Pose2d(12.545, 2.854,Rotation2d.fromDegrees(54.545))});
      //   reefPolePositions.put(17, new Pose2d[]{new Pose2d(3.706, 3.016,Rotation2d.fromDegrees(57.529)), new Pose2d(3.983, 2.865,Rotation2d.fromDegrees(57.529))});
      //   reefPolePositions.put(18, new Pose2d[]{new Pose2d(3.186, 4.133,Rotation2d.fromDegrees(1.893)), new Pose2d(3.218, 3.900,Rotation2d.fromDegrees(1.893))});
      //   reefPolePositions.put(19, new Pose2d[]{new Pose2d(3.950, 5.202,Rotation2d.fromDegrees(-60)), new Pose2d(3.722, 5.050,Rotation2d.fromDegrees(-60))});
      //   reefPolePositions.put(20, new Pose2d[]{new Pose2d(5.263, 5.033,Rotation2d.fromDegrees(-120)), new Pose2d(4.964, 5.213,Rotation2d.fromDegrees(-115))});
      //   reefPolePositions.put(21, new Pose2d[]{new Pose2d(5.722, 3.9,Rotation2d.fromDegrees(-180)), new Pose2d(5.783, 4.171,Rotation2d.fromDegrees(-180))});
      //   reefPolePositions.put(22, new Pose2d[]{new Pose2d(4.964, 2.837,Rotation2d.fromDegrees(117.681)), new Pose2d(5.241, 2.984,Rotation2d.fromDegrees(117.681))});
      // }

      public static final double poleToTag = (0.1651);
      public static final HashMap<Integer, Pose2d[]> reefPolePositions = new HashMap();

      static { //branches are ordered left to right
        reefPolePositions.put(7, new Pose2d[]{new Pose2d(13.524, 2.830,Rotation2d.fromDegrees(117.987)), new Pose2d(13.833, 3.003,Rotation2d.fromDegrees(117.987))});
        reefPolePositions.put(8, new Pose2d[]{new Pose2d(14.324, 3.859,Rotation2d.fromDegrees(-178.596)), new Pose2d(14.337, 4.190,Rotation2d.fromDegrees(-178.596))});
        reefPolePositions.put(9, new Pose2d[]{new Pose2d(13.884, 5.015,Rotation2d.fromDegrees(-127.926)), new Pose2d(13.567, 5.216,Rotation2d.fromDegrees(-121.350))});
        reefPolePositions.put(10, new Pose2d[]{new Pose2d(12.534, 5.216,Rotation2d.fromDegrees(-56.014)), new Pose2d(12.265, 5.041,Rotation2d.fromDegrees(-56.014))});
        reefPolePositions.put(11, new Pose2d[]{new Pose2d(11.771, 4.165,Rotation2d.fromDegrees(0)), new Pose2d(11.773, 3.859,Rotation2d.fromDegrees(0))});
        reefPolePositions.put(12, new Pose2d[]{new Pose2d(12.223, 3.049,Rotation2d.fromDegrees(54.545)), new Pose2d(12.545, 2.854,Rotation2d.fromDegrees(54.545))});
        reefPolePositions.put(1, new Pose2d[]{new Pose2d(3.706, 3.016,Rotation2d.fromDegrees(57.529)), new Pose2d(3.983, 2.865,Rotation2d.fromDegrees(57.529))});
        reefPolePositions.put(2, new Pose2d[]{new Pose2d(3.186, 4.133,Rotation2d.fromDegrees(1.893)), new Pose2d(3.218, 3.900,Rotation2d.fromDegrees(1.893))});
        reefPolePositions.put(3, new Pose2d[]{new Pose2d(3.950, 5.202,Rotation2d.fromDegrees(-60)), new Pose2d(3.722, 5.050,Rotation2d.fromDegrees(-60))});
        reefPolePositions.put(4, new Pose2d[]{new Pose2d(5.263, 5.033,Rotation2d.fromDegrees(-120)), new Pose2d(4.964, 5.213,Rotation2d.fromDegrees(-115))});
        reefPolePositions.put(5, new Pose2d[]{new Pose2d(5.722, 3.9,Rotation2d.fromDegrees(-180)), new Pose2d(5.783, 4.171,Rotation2d.fromDegrees(-180))});
        reefPolePositions.put(6, new Pose2d[]{new Pose2d(4.964, 2.837,Rotation2d.fromDegrees(117.681)), new Pose2d(5.241, 2.984,Rotation2d.fromDegrees(117.681))});
      }
  }

  public static final class MotionConstants {
    public static final HashMap<String, Double> l4 = new HashMap();
    static{
      l4.put("elevator", 1.42);
      l4.put("wrist", 150.0);
    }

    public static final HashMap<String, Double> l3 = new HashMap();
    static{
      l3.put("elevator", 0.48);
      l3.put("wrist", 180.0);
    }

    public static final HashMap<String, Double> l2 = new HashMap();
    static{
      l2.put("elevator", 0.08);
      l2.put("wrist", 180.0);
    }

    public static final HashMap<String, Double> intake = new HashMap();
    static{
      intake.put("elevator", 0.18);
      intake.put("wrist", 10.0);
    }

    public static final HashMap<String, Double> intakeClearanceOut = new HashMap();
    static{
      intakeClearanceOut.put("elevator", 0.31);
      intakeClearanceOut.put("wrist", 180.0);
    }

    public static final HashMap<String, Double> intakeClearanceIn = new HashMap();
    static{
      intakeClearanceIn.put("elevator", 0.31);
      intakeClearanceIn.put("wrist", 10.0);
    }

    public static final HashMap<String, Double> stowed = new HashMap();
    static{
      stowed.put("elevator", 0.0);
      stowed.put("wrist", 0.0);
    }
  }

  public static final class ElevatorConstants {
    public static final double discDiameterMeter = Units.inchesToMeters(2.082);
    public static final double discCircumferenceMeter = discDiameterMeter * Math.PI;
    public static final double discGearRatio = 9; 
    public static final int elevatorMotorLeftCanId = 10;
    public static final int elevatorMotorRightCanId = 11;
    
    public static final double elevatorKp = 0.01;
    public static final double elevatorKi = 0;
    public static final double elevatorKd = 0;
    public static final double elevatorKMinOutput = -1.0;
    public static final double elevatorKMaxOutput = 1.0;
    public static final double elevatorKf = 0.5;
    public static final double maxVel = 0;
    public static final double maxAccel = 0;
    public static final double allowedErr = 1;
  }
  public static final class WristConstants{
    public static final int wristMotorCANId = 12;//Temp
    
    public static final double kP = 0.1;
    public static final double kI = 0;
    public static final double kD = 0;
    public static final double minOutput = -1;
    public static final double maxOutput = 1;
    public static final double velocityFF = 0.5;
  }

  public static final class IntakeConstants {
    public static final int coralMotorCANID = 14;
    public static final int algaeMotorCANID = 13;
  }

  public static final class DriveConstants {
    // Driving Parameters - Note that these are not the maximum capable speeds of
    // the robot, rather the allowed maximum speeds
    public static final double kMaxSpeedMetersPerSecond = 2;
    public static final double kMaxAngularSpeed = 2 * Math.PI; // radians per second
    


    // Chassis configuration
    public static final double kTrackWidth = Units.inchesToMeters(24.5);
    // Distance between centers of right and left wheels on robot
    public static final double kWheelBase = Units.inchesToMeters(24.5);
    // Distance between front and back wheels on robot
    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
        new Translation2d(kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

    // Angular offsets of the modules relative to the chassis in radians
    public static final double kFrontLeftChassisAngularOffset = -Math.PI / 2;
    public static final double kFrontRightChassisAngularOffset = 0;
    public static final double kBackLeftChassisAngularOffset = Math.PI;
    public static final double kBackRightChassisAngularOffset = Math.PI / 2;

    // SPARK MAX CAN IDs
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
    // The MAXSwerve module can be configured with one of three pinion gears: 12T,
    // 13T, or 14T. This changes the drive speed of the module (a pinion gear with
    // more teeth will result in a robot that drives faster).
    public static final int kDrivingMotorPinionTeeth = 14;

    // Calculations required for driving motor conversion factors and feed forward
    public static final double kDrivingMotorFreeSpeedRps = NeoMotorConstants.kFreeSpeedRpm / 60;
    // public static final double kWheelDiameterMeters = 0.0713;
    public static final double kWheelDiameterMeters = 0.07346;
    public static final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;
    // 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15
    // teeth on the bevel pinion
    public static final double kDrivingMotorReduction = (45.0 * 22) / (kDrivingMotorPinionTeeth * 15);
    public static final double kDriveWheelFreeSpeedRps = (kDrivingMotorFreeSpeedRps * kWheelCircumferenceMeters)
        / kDrivingMotorReduction;
  }

  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;
    public static final double kDriveDeadband = 0.05;
  }

  public static final class AutoConstants {
    public static final double kMaxSpeedMetersPerSecond = 2;
    public static final double kMaxAccelerationMetersPerSecondSquared = 3;
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

    // Constraint for the motion profiled robot angle controller
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
        kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
  }

  public static final class NeoMotorConstants {
    public static final double kFreeSpeedRpm = 5676;
  }
 


}
