
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.XboxController;


public class Climb extends Command{

    private final DriveSubsystem driveSubsystem;
    private final XboxController controller;

    public Climb(DriveSubsystem driveSubsystem, XboxController controller)
    {
        this.driveSubsystem = driveSubsystem;
        this.controller = controller;

        addRequirements(driveSubsystem);
    }

    
    public void initialize() {

    }

    public void execute() {

    driveSubsystem.climb(0.5);
        
    }

    public void end() {
        driveSubsystem.climb(0);
    }
    
    public boolean hasFinished() {
        return controller.getPOV() == -1;
    }
}
