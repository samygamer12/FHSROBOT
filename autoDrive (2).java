package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Timer;


public class autoDrive extends Command {
    

private final DriveSubsystem driveSubsystem;
private final Timer timer = new Timer();

  public autoDrive(DriveSubsystem driveSubsystem) {
   //parameters added here to be usable in RobotContainer with right inputs and stuff.

    this.driveSubsystem = driveSubsystem;
   
    
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveSubsystem); 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  
 double left =    .5;
 double right = - .5; 
 //rightTriggerAxis is rightY and leftTriggerAxis is rightX
 driveSubsystem.manualDrive(left, right);
 

  }

// Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    driveSubsystem.manualDrive(0, 0);
    timer.stop();
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.hasElapsed(5);
  }
}

