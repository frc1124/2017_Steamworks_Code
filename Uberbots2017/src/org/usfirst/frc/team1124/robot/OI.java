package org.usfirst.frc.team1124.robot;


import org.usfirst.frc.team1124.robot.commands.TargetVisionTape;
import org.usfirst.frc.team1124.robot.commands.ToggleGearDoor;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {

	// The Controller
	public static Joystick stick = new Joystick(0);
	Button targetTrigger = new JoystickButton(stick, 1);
	Button toggleGearDoor = new JoystickButton(stick, 2);

	public OI() {
		targetTrigger.whenPressed(new TargetVisionTape());
		toggleGearDoor.whenPressed(new ToggleGearDoor());
	}

}
