package frc.robot.util;

import edu.wpi.first.wpilibj.Joystick;


public class OI {
    public Joystick stick;

    public OI() {
        stick = new Joystick(RoboMap.joystickPort); // Ensure this matches the port in RoboMap
    }
}
