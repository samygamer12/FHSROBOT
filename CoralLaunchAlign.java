package frc.robot.commands;



import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralSubsystem;
import edu.wpi.first.wpilibj.Timer;

public class CoralLaunchAlign extends Command
{
      
    private final CoralSubsystem coralSubsystem;
    private final Timer timer = new Timer();

    public CoralLaunchAlign(CoralSubsystem coralSubsystem)
    {
        
        this.coralSubsystem = coralSubsystem;
        addRequirements(coralSubsystem);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
    }
    @Override
    public void execute() {
        
        coralSubsystem.coralLaunchAlign();
    }
    
  @Override
  public void end(boolean interrupted) {
    coralSubsystem.stopLaunch();
    timer.stop();

    
  }

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(0.1);
  }


 
    
}
