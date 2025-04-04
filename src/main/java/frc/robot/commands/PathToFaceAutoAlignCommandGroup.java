package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;

public class PathToFaceAutoAlignCommandGroup extends SequentialCommandGroup {
    public PathToFaceAutoAlignCommandGroup(boolean isRight, DriveSubsystem m_driveSubsystem, LimeLightSubsystem m_lightSubsystem){
        addCommands(
            m_driveSubsystem.findPathToFace(),
            new AutoAlignToReefTagRelative(isRight, m_driveSubsystem, m_lightSubsystem)
        );
    }
}