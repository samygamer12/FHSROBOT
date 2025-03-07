package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralSubsystem;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.ArmSubsystem;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

public class ArmMove extends Command {

    private double angle = 0;
    private final Encoder encoder;
    private final ArmSubsystem armSubsystem;
   // private final PIDController test;

    public ArmMove(Encoder encoder, ArmSubsystem armSubsystem) {
        
        this.encoder = encoder;
        this.armSubsystem = armSubsystem;

        //256 will change depending on the pulse tick speed of the encoder
        encoder.setDistancePerPulse(360.0 / 256.0);
        

        addRequirements(armSubsystem);
    }

    @Override
    public void initialize() {
       
    }
    @Override
    public void execute() {
        angle = encoder.getDistance();

        

       
    }
    
  @Override
  public void end(boolean interrupted) {
    
    
  }

  @Override
  public boolean isFinished() {
    return false;

  }


}








