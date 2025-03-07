package frc.robot.commands.TestCommands;

import com.studica.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.util.RoboMap;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

//basically sensor distance is getVoltage();

public class SensorTest extends Command {
  private final DriveSubsystem driveSubsystem;
  private final XboxController controller;
  private AnalogInput ultraSonicSensor;
  private AHRS navX;

  public SensorTest(DriveSubsystem driveSubsystem, XboxController controller, AnalogInput ultraSonicSensor, AHRS navX) {
    this.driveSubsystem = driveSubsystem;
    this.controller = controller;
    this.ultraSonicSensor = ultraSonicSensor;
    this.navX = navX;
    addRequirements(driveSubsystem);
  }

  @Override
  public void initialize() {
    //reset sensor
    navX.zeroYaw();

    
  }

  @Override
  public void execute() {
   

double voltage;

   voltage = ultraSonicSensor.getVoltage();
   System.out.println("Distance: " + voltage);
   System.out.println("Yaw: " + navX.getYaw());
   


}




  

  @Override
  public boolean isFinished() {
    return controller.getBButton();
  }

  @Override
  public void end(boolean interrupted) {
    
  }
}


