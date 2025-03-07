package frc.robot.commands;


import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkBase.ControlType;
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
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

public class AutoDropOffStraight extends Command {
    

    private final DriveSubsystem driveSubsystem;
    private final CoralSubsystem coralSubsystem;
    private Timer timer;
    private SparkClosedLoopController pidControllerLeft;
    private SparkClosedLoopController pidControllerRight;
    
  
  private AnalogInput ultraSonicSensor; //distance thing
  private final AHRS navX; //gyro and acceleration
  private boolean collision; //make it stop immediately if it detects collision so it doesnt keep going.

   

  public AutoDropOffStraight(DriveSubsystem driveSubsystem, CoralSubsystem coralSubsystem, 
  AnalogInput ultraSonicSensor, AHRS navX, 
  SparkClosedLoopController pidControllerLeft, SparkClosedLoopController pidControllerRight) {
    this.driveSubsystem = driveSubsystem;
    this.coralSubsystem = coralSubsystem;
    this.ultraSonicSensor = ultraSonicSensor;
    this.navX = navX;
    this.pidControllerLeft = pidControllerLeft;
    this.pidControllerRight = pidControllerRight;
    
  

    
    
    
    addRequirements(driveSubsystem);
  }


  @Override
  public void initialize() {
    collision = false;
    
    timer.reset();
    timer.start();
    navX.reset();
  }
  @Override
  public void execute() {
    
    double finalSpeedPer = 0;

    //forwards for certain amount of timeto aling in
    while (!timer.hasElapsed(2))
    {
      driveSubsystem.manualDrive(-0.5, 0.5);
    }
    new WaitCommand(2);
    

    //slow down as it approaches the reef
   while (timer.getVoltage() < 1.75 && timer.getVoltage() > 0.75 && finalSpeedPer > 0)
    {

      finalSpeedPer -= 0.001;
      driveSubsystem.manualDrive(-finalSpeedPer, finalSpeedPer);
   }

    
    

  }

  @Override
  public void end(boolean interrupted) {

    driveSubsystem.manualDrive(0, 0);
    new WaitCommand(3);
    new CoralLaunch(coralSubsystem);

    timer.reset();
    timer.start();
    while (timer.hasElapsed(3));
    {
    driveSubsystem.manualDrive(0.5, -0.5);
    }

    
    
    
  
     
    

    
    
  }

  @Override
  public boolean isFinished() {
    return ultraSonicSensor.getVoltage() < 0.6;
  }
}

