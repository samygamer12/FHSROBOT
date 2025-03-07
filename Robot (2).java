package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.apriltag.AprilTagDetection;
import edu.wpi.first.apriltag.AprilTagDetector;
import edu.wpi.first.cameraserver.CameraServer;



import frc.robot.util.OI;






/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
  
public class Robot extends TimedRobot {
  //public static final DriveSubsystem driveSubsystem = new DriveSubsystem();
  public static OI oi;
  private Command autonomousCommand; 
  //SendableChooser<Command> chooser = new SendableChooser<>();
  private RobotContainer robotContainer;
  private static final String SimpleAuto = "Simple"; //These 2 lines create variables that allow the robot to choose between 2 tasks
  private static final String AutoStraight = "AutoStraight"; //for autonomous mode.
  private static final String AutoTRight = "AutoTurnRight";
  private static final String AutoTLeft = "AutoTurnLeft";

  
  

  public static String autoSelected;
  public static final SendableChooser<String> chooser = new SendableChooser<>();  //This is where we pick one of the tasks (the menu).
  


  @Override
public void robotInit() {
    oi = new OI();  // This must run before any joystick calls
    SmartDashboard.putData("Auto mode", chooser);
    robotContainer = new RobotContainer();
    chooser.setDefaultOption("Simple Auto", SimpleAuto);
   chooser.addOption("Straight Auto", AutoStraight);
   chooser.addOption("Turn Right Auto", AutoTRight);
   chooser.addOption("Turn Left Auto", AutoTLeft);
   SmartDashboard.putData("Auto choices", chooser);
   SmartDashboard.putData("Auto Command", robotContainer.getAutonomousCommand());
}

 // private final RobotContainer m_robotContainer
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  

  public Robot() {
    // Instantiate our RobotContainer. This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
   
    //Smart dashboard is where all the tasks are (the menu). This code sets up the menu.
    
    enableLiveWindowInTest(true);
    CameraServer.startAutomaticCapture();



    //start camera 
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated, and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */


  
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods. This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();

  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    // m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    // schedule the autonomous command (example)
    // if (m_autonomousCommand != null) {
    //     m_autonomousCommand.schedule();
    // }
    autoSelected = chooser.getSelected(); //This lets the robot choose from the menu what it wants
    System.out.println("Auto selected: " + autoSelected); //This prints out what option it chose from the menu.
     autonomousCommand = robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (autonomousCommand != null) {
      autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {

  
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
   if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
    
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    //System.out.println("Hello World!");
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    //m_robotContainer.m_driverController.leftStick().whileTrue(new DriveManuallyCommand());
    //System.out.println(robotContainer.m_driverController.getRightTriggerAxis());
    
   
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}

  