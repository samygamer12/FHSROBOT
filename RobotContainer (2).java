// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//auto commands
import frc.robot.commands.AutoDropOffStraight;
import frc.robot.commands.AutoDropOffTurnLeft;
import frc.robot.commands.AutoDropOffTurnRight;
//command imports
import frc.robot.commands.Autos;
import frc.robot.commands.Climb;
import frc.robot.commands.DriveManuallyCommand;
import frc.robot.commands.CoralLaunch;
import frc.robot.commands.CoralLaunchAlign;
import frc.robot.commands.CoralLaunchWeak;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.autoDrive;


//test commands
import frc.robot.commands.TestCommands.AutoTestPrints;
import frc.robot.commands.TestCommands.TestDrive;
import frc.robot.commands.TestCommands.SensorTest;

//subsystems
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.CoralSubsystem;
import frc.robot.subsystems.DriveSubsystem;

//wpilib stuff
import frc.robot.util.RoboMap;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.XboxController;

//gyro
import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;

//pidcontroller
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkBase.ControlType;

//camera
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;

//dashboard
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */

// A simple auto routine that drives forward a specified distance, and then stops.
  
public class RobotContainer {
  // Replace with CommandPS4Controller or CommandJoystick if needed
  public final XboxController driverController =
     new XboxController(RoboMap.joystickPort);

     //subsystems
     private final DriveSubsystem driveSubsystem = new DriveSubsystem(driverController);
     private final CoralSubsystem coralSubsystem = new CoralSubsystem(driverController);

     //sensors
     private AnalogInput ultraSonicSensor = new AnalogInput(RoboMap.ultraSonicSensorPort);
     public final static AHRS navX = new AHRS(NavXComType.kMXP_SPI);

    //pid controllers
    private final SparkClosedLoopController pidControllerDriveTrainLeft = driveSubsystem.pidControllerLeftGroup;
    private final SparkClosedLoopController pidControllerDriveTrainRight = driveSubsystem.pidControllerLeftGroup;

     //simple is back off line. Complex is drop off
     private final autoDrive simpleAuto =
      new autoDrive(driveSubsystem);

      private final AutoDropOffStraight autoDropStraight = new AutoDropOffStraight(driveSubsystem, coralSubsystem, 
      ultraSonicSensor, navX, pidControllerDriveTrainLeft, pidControllerDriveTrainRight);

      private final AutoDropOffTurnLeft autoDropTLeft = new AutoDropOffTurnLeft(driveSubsystem, coralSubsystem, 
      ultraSonicSensor, navX);

      private final AutoDropOffTurnRight autoDropTRight = new AutoDropOffTurnRight(driveSubsystem, coralSubsystem, 
      ultraSonicSensor, navX);

      
      public final CommandXboxController test = null;
      /*controller is not the same as xbox controller
      *please cross reference for right commands and values
      */

  //we don't have a complexAuto lol
  //private final Command complexAuto = new ComplexAuto(robotDrive, hatchSubsystem);

 /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {

    String chosen =Robot.chooser.getSelected();
    if (chosen.equals("Simple"))
    {
      return simpleAuto;
    }
    else if (chosen.equals("AutoStraight"))
    {
      return autoDropStraight;
    }
    else if (chosen.equals("AutoTurnRight"))
    {
      return autoDropTRight;
    }
    else if (chosen.equals("AutoTurnLeft"))
    {
      return autoDropTLeft;
    }
    else
    {
      return new AutoTestPrints(driveSubsystem, chosen);
    }
 }


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    
    /*new Trigger(m_ExampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_ExampleSubsystem));
*/
    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    driveSubsystem.setDefaultCommand(
      new DriveManuallyCommand(driveSubsystem, driverController)); //Always active

      //coral launch on Xpress
      new Trigger(coralSubsystem::coralLaunchButtonPressed).onTrue(new CoralLaunch(coralSubsystem));

      //print the distance for now test panic
      //y press
      new Trigger(driveSubsystem::getSensorTestButton).onTrue(new SensorTest(driveSubsystem, driverController, ultraSonicSensor, navX));
     
      //A press
      new Trigger(coralSubsystem::coralLaunchButtonPressedAlign).onTrue(new CoralLaunchAlign(coralSubsystem));

      //b presss
      new Trigger(coralSubsystem::coralLaunchButtonPressedWeak).onTrue(new CoralLaunchWeak(coralSubsystem));

      //upArro press
      new Trigger(driveSubsystem::climbButtonPressed).onTrue(new Climb(driveSubsystem, driverController));
      

      //getA is actuall X
      //getB is actually y
      //getY is actually A
      //getX is actually B
        
  }
   //m_driverController.leftStick().whileTrue(new DriveManuallyCommand());
   //m_driverController.rightStick().whileTrue(new DriveManuallyCommand());
  
}