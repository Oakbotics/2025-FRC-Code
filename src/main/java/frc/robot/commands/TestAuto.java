package frc.robot.commands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;

public class TestAuto extends SequentialCommandGroup {
    public TestAuto(DriveSubsystem m_driveSubsystem, LimeLightSubsystem m_lightSubsystem){
        addCommands(
            // m_driveSubsystem.findPathToPose(14.35, 4.170, 0, false),
            new PathPlannerAuto("3P Middle Top Bottom")
        );
    }
}

