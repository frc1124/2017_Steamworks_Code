package org.usfirst.frc.team1124.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class RobotMap {
	//odd on the left, high in the back (1 is left front, 4 is back right)
	public static SpeedController wheelOne;
    public static SpeedController wheelTwo;
    public static SpeedController wheelThree;
    public static SpeedController wheelFour;
    public static RobotDrive mechDrive;
    
    //OI
    public static Joystick stick;
    
    public static void init() {
    	//DriveTrain
    	wheelOne = new Talon(0);
    	wheelTwo = new Talon(1);
    	wheelThree = new Talon(2);
    	wheelFour = new Talon(3);
    	
    	mechDrive = new RobotDrive(wheelOne,wheelTwo,wheelThree,wheelFour);
    	mechDrive.setSafetyEnabled(true);
    	mechDrive.setExpiration(0.1);
    	mechDrive.setMaxOutput(1.0);
    	mechDrive.setSensitivity(0.5);
    	
    	//OI
    	stick = new Joystick(0);
    }
}
