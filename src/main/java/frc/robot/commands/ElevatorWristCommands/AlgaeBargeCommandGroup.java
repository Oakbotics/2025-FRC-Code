// package frc.robot.commands.ElevatorWristCommands;

// import edu.wpi.first.wpilibj2.command.Commands;
// import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import frc.robot.subsystems.WristSubsystem;
// import frc.robot.subsystems.ElevatorSubsystem;
// import frc.robot.subsystems.IntakeSubsystem;

// public class AlgaeBargeCommandGroup extends SequentialCommandGroup {
//     public AlgaeBargeCommandGroup(ElevatorSubsystem m_elevatorSubsystem, WristSubsystem m_wristSubsystem, IntakeSubsystem m_intakeSubsystem){
//         addCommands(
//             new ParallelCommandGroup(
//                 new ElevatorPositionCommand(m_elevatorSubsystem, 0.16),
//                 new WristPositionCommand(m_wristSubsystem, 100).onlyIf(() -> (m_wristSubsystem.getWristAngle() < 95 || m_wristSubsystem.getWristAngle() > 105))
//                 ),
//             new ParallelCommandGroup(
//                 new ElevatorPositionCommand(m_elevatorSubsystem, 1.49),
//                 new WristPositionCommand(m_wristSubsystem, 100),
//                 new SequentialCommandGroup(
//                     new AlgaeIntakeCommand(m_intakeSubsystem).withTimeout(0.5),
//                     new AlgaeOuttakeCommand(m_intakeSubsystem).withTimeout(1)   
//                 )
//             )
//         );

//     }
// }