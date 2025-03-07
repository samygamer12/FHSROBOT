package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.RoboMap;

public class CoralSubsystem extends SubsystemBase{

    public SparkMax coralLauncher = new SparkMax(RoboMap.coralLauncher, MotorType.kBrushed);
    private final XboxController controller;  

public CoralSubsystem(XboxController controller) {
this.controller = controller;
}

public void coralLaunch()
{
    coralLauncher.setVoltage(10);
}
public void coralLaunchWeak()
{
    coralLauncher.setVoltage(5);
}
public void coralLaunchAlign()
{
    coralLauncher.setVoltage(-3);
}

public boolean coralLaunchButtonPressed() {

    return controller.getAButtonPressed();

}
public boolean coralLaunchButtonPressedWeak() {

    return controller.getXButtonPressed();

}

public boolean coralLaunchButtonPressedAlign() {

    return controller.getYButtonPressed();

}

public void stopLaunch() {
    coralLauncher.setVoltage(0);

}


    
}
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.




