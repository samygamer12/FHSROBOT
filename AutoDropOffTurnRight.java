package frc.robot.commands;


import com.revrobotics.spark.SparkAnalogSensor;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
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

public class AutoDropOffTurnRight extends Command {
    

    private final DriveSubsystem driveSubsystem;
    private final CoralSubsystem coralSubsystem;
    private Timer timer;
    private SparkClosedLoopController pidControllerLeft;
    private SparkMaxConfig config = new SparkMaxConfig();
    private SparkAnalogSensor sensor;
    


    //temp
    private SparkMax temp = new SparkMax(1, MotorType.kBrushless);
    
  
  private AnalogInput ultraSonicSensor; //distance thing
  private final AHRS navX; //gyro and acceleration
  private boolean collision; //make it stop immediately if it detects collision so it doesnt keep going.

   

  public AutoDropOffTurnRight(DriveSubsystem driveSubsystem, CoralSubsystem coralSubsystem, 
  AnalogInput ultraSonicSensor, AHRS navX) {

    this.driveSubsystem = driveSubsystem;
    this.coralSubsystem = coralSubsystem;
    this.ultraSonicSensor = ultraSonicSensor;
    this.navX = navX;
    this.pidControllerLeft = driveSubsystem.pidControllerLeftGroup;
    
    
          
         

    
  

    
    
    
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
    
    
    //this section is used to turn certain angle
    //it continuously checks the angle
    //it also runs every .1 seconds
    //takes current, compares to desired, updates accordingly
    while (navX.getYaw() > -70)
    {
    double currentAngle = navX.getYaw();

    //change the first value based on how yaw works
    double error = -80 + currentAngle;
    if (error > 180) {
      error -= 360;
  } else if (error < -180) {
      error += 360;
  }


    //convert output range to 1-0 by percents
    double output = error /360;
    output = Math.max(-1.0, Math.min(1.0, output));

    //chnges the setpoint of the motor accordingly until it gets to output value
   
  
    pidControllerLeft.setReference(output, ControlType.kDutyCycle);

    new WaitCommand(0.1);
  }

    
    
    

    

    //slow down as it approaches the reef
   while (ultraSonicSensor.getVoltage() < 1.75 && ultraSonicSensor.getVoltage() > 0.75 && finalSpeedPer > 0)
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

