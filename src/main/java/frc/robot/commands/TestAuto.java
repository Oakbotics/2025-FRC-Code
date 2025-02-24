package frc.robot.commands;

import com.pathplanner.lib.commands.PathPlannerAuto;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;

public class TestAuto extends SequentialCommandGroup {
    
    public TestAuto(DriveSubsystem m_driveSubsystem, LimeLightSubsystem m_lightSubsystem){
        //PathPlannerAuto auto = new PathPlannerAuto("3P Middle Top Bottom");
        PathPlannerAuto auto = new PathPlannerAuto("1P Middle Middle");

        addCommands(
            m_driveSubsystem.findPathToPose(auto.getStartingPose(), DriverStation.getAlliance().get() == Alliance.Red),
            auto
        );
    }
}

