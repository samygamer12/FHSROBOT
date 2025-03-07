package frc.robot.commands.TestCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Timer;


public class AutoTestPrints extends Command {
    
private String choice;
private DriveSubsystem driveSubsystem;

private final Timer timer = new Timer();

  public AutoTestPrints(DriveSubsystem driveSubsystem, String choice) {
   //parameters added here to be usable in RobotContainer with right inputs and stuff.
    this.driveSubsystem = driveSubsystem;
    this.choice = choice;
   
    
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveSubsystem); 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
    System.out.println(choice + " has been initiated");
  }


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  
 System.out.println(choice + "is currently running");
 

  }

// Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

   
    timer.stop();
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.hasElapsed(3);
  }
}

