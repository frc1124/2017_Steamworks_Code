package org.usfirst.frc.team1124.robot;

import org.usfirst.frc.team1124.robot.commands.Climb;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	public static Joystick firstDriver = new Joystick(0);
	public static Joystick secondDriver = new Joystick(1);
	
	public static Button climb = new JoystickButton(firstDriver,RobotMap.firstDriverA);
	public OI() {
		climb.whileHeld(new Climb(true));
	}
}

