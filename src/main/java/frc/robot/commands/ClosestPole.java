// package frc.robot.commands;

// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.wpilibj.DriverStation;
// import frc.robot.subsystems.DriveSubsystem;

// public class ClosestPole extends Command {

//     private final DriveSubsystem drive;
//     private final boolean alignLeft;

//     private Pose2d closestPole;

//     public ClosestPole(DriveSubsystem drive, boolean alignLeft) {
//         this.drive = drive;
//         this.alignLeft = alignLeft;
   
//         addRequirements(drive);
//     }

//     @Override 
//     public void initialize() {
//         boolean isBlue = DriverStation.getAlliance().orElse(DriverStation.Alliance.Blue) == DriverStation.Alliance.Blue;
//     }

// }
