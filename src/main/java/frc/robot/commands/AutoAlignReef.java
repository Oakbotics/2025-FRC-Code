// package frc.robot.commands;

// import com.pathplanner.lib.auto.AutoBuilder;
// import com.pathplanner.lib.commands.PathPlannerAuto;
// import com.pathplanner.lib.path.PathPlannerPath;

// import edu.wpi.first.wpilibj.DriverStation;
// import edu.wpi.first.wpilibj.DriverStation.Alliance;
// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import frc.robot.subsystems.DriveSubsystem;
// import frc.robot.subsystems.LimeLightSubsystem;

// public class AutoAlignReef extends SequentialCommandGroup {
    
//     public AutoAlignReef(DriveSubsystem m_driveSubsystem, LimeLightSubsystem m_lightSubsystem, boolean isLeft){
        
//         addCommands(
//             m_driveSubsystem.findPathToPole(isLeft)
//         );
//     }
// }

