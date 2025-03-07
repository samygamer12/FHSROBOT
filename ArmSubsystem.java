package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkClosedLoopController;



import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.RoboMap;

public class ArmSubsystem extends SubsystemBase{

    private final XboxController controller;
    private final SparkMax armMotor = new SparkMax(RoboMap.armMotorPort, MotorType.kBrushless);
    private final SparkAbsoluteEncoder encoderRaw = armMotor.getAbsoluteEncoder();
    
    
    //idk bro it aint configure. Just configure it directly
    
    private final  SparkClosedLoopController pidController = armMotor.getClosedLoopController();


    
    

    public ArmSubsystem(XboxController controller)
    {
        this.controller = controller;
        
    }

    public boolean getArmMoveDown() {

        return controller.getYButtonPressed();

    }
    
    public void moveArmDown() {

        
    }



}