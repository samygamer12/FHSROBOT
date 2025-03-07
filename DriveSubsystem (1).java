package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;


import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkMaxConfigAccessor;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.studica.frc.AHRS;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.util.RoboMap;
//import edu.wpi.first.wpilibj.SpeedControllerGroup;// Dear future coder, FUCK THIS SHIT fucking piece of fucking dogshit :)

public class DriveSubsystem extends SubsystemBase
{
    private final SparkMax leftGroup = new SparkMax(RoboMap.leftMasterPort, MotorType.kBrushed);
    private final SparkMax leftSlave = new SparkMax(RoboMap.leftSlavePort, MotorType.kBrushed);
    private final SparkMax rightGroup = new SparkMax(RoboMap.rightMasterPort, MotorType.kBrushed);
    private final SparkMax rightSlave = new SparkMax(RoboMap.rightSlavePort, MotorType.kBrushed);

    //pid controllers
    public SparkClosedLoopController pidControllerLeftGroup = leftGroup.getClosedLoopController();
    public SparkClosedLoopController pidControllerRightGroup = leftGroup.getClosedLoopController();
    

    public final SparkMax climber = new SparkMax(RoboMap.climberPort, MotorType.kBrushless);
    private SparkBaseConfig climbConfig = new SparkMaxConfig();

   

    

    private final XboxController controller;  
    
    
//private Victor Lgroup = new Victor() ;      //Left side variable of tank
//private Victor Rgroup= new Victor();        //Right side variable of tank
                                            //We need to develop a file for all the port number constants to call as arguments
    //SpeedControllerGroup leftMotorGroup = new SpeedControllerGroup(leftGroup, leftSlave);
           
    public final DifferentialDrive drive = new DifferentialDrive(leftGroup, rightGroup);  
    //important to note that left side is inverted

    public DriveSubsystem(XboxController controller) {
        //point slaves to masters
        
        leftSlave.set(leftGroup.get()); //same thing as .follow
        rightSlave.set(rightGroup.get());
        this.controller = controller;

        climbConfig.idleMode(IdleMode.kBrake);

        climber.configure(climbConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    
        
        
    }

    
    //temp for testing senseor
    public boolean getSensorTestButton() {
       return controller.getBButtonPressed();
        
    }

    public void manualDrive(double left, double right){
        //if(move > 0.05) move = 0.05;
        //if(turn > 0.5) turn = -1;
        drive.tankDrive(left, right);
    }

    //just so commands can access arcadeDrive without having to define driveSystems
    public void arcadeDrive(double forward, double turn)
    {
        drive.arcadeDrive(forward, turn);
    }
    public void periodic() {
        drive.feed();
    }

    public void stop()
    {
        drive.tankDrive(0,0);
    }


    public boolean climbButtonPressed(){
        return (controller.getPOV() == 0);
    }
    public void climb(double speed)
    {
        climber.set(speed);
    }

    
    
}
