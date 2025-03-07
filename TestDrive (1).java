package frc.robot.commands.TestCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class TestDrive extends Command {
  private final DriveSubsystem driveSubsystem;
  private final CommandXboxController controller;

  public TestDrive(DriveSubsystem driveSubsystem, CommandXboxController controller) {
    this.driveSubsystem = driveSubsystem;
    this.controller = controller;
    addRequirements(driveSubsystem);
  }

  @Override
  public void initialize() {
    System.out.println("TestDriveCommand initialized");
  }

  @Override
  public void execute() {
    driveSubsystem.manualDrive(.5, 1); // Drive forward at 50% speed
    System.out.println("TestDriveCommand executing: Driving forward");
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    driveSubsystem.manualDrive(0, 0); // Stop motors
    System.out.println("Test ended");
  }
}
