package frc.robot.commands;


import com.studica.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.util.RoboMap;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.CoralSubsystem;

public class AutoDropOff extends Command {
    

    private final DriveSubsystem driveSubsystem;
    private final CoralSubsystem coralSubsystem;
  private final Timer timer = new Timer();
  private AnalogInput ultraSonicSensor;
  private final AHRS navX;
  private boolean collision;

  public AutoDropOff(DriveSubsystem driveSubsystem, CoralSubsystem coralSubsystem, AnalogInput ultraSonicSensor, AHRS navX) {
    this.driveSubsystem = driveSubsystem;
    this.coralSubsystem = coralSubsystem;
    this.ultraSonicSensor = ultraSonicSensor;
    this.navX = navX;
    
    
    
    addRequirements(driveSubsystem);
  }


  @Override
  public void initialize() {
    collision = false;
    
  }
  @Override
  public void execute() {
    
    double speedPer = 0;
    while (speedPer < 0.75)
    {
      speedPer += 0.15;
    }
    driveSubsystem.manualDrive(speedPer, speedPer);

    while (ultraSonicSensor.getVoltage() < 1.75 && ultraSonicSensor.getVoltage() > 0 && speedPer > 0)
    {
      speedPer -= 0.15;
    }

    if (navX.getRawAccelX() < -1.5)
    {
      collision = true;
    }

  }

  @Override
  public void end(boolean interrupted) {

    driveSubsystem.manualDrive(0, 0);
    new WaitCommand(1);
    coralSubsystem.coralLaunch();
    new WaitCommand(1);
      double speedPer = 0;
      while (speedPer > 0)
      {
        speedPer -= 0.15;
      }
      driveSubsystem.manualDrive(speedPer, speedPer);
  
     
    

    
    
  }

  @Override
  public boolean isFinished() {
    return ultraSonicSensor.getVoltage() < .40 || collision;
  }
}

