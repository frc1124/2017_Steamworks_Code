package org.usfirst.frc.team1124.robot;
import org.usfirst.frc.team1124.robot.commands.TargetVisionTape;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


public class OI {

	// The Controller
	public static Joystick stick = new Joystick(0);
<<<<<<< HEAD
	public OI() {}
=======
	Button targetTrigger = new JoystickButton(stick, 0);
	
	public OI() {
		targetTrigger.whenPressed(new TargetVisionTape());
	}

	
>>>>>>> master
}
