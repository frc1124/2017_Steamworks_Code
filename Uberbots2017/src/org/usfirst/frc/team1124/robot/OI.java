package org.usfirst.frc.team1124.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	public static Joystick stick;
	public static Button trigger;

	public OI() {
		stick = new Joystick(0);
		trigger = new JoystickButton(stick, 1);
	}
}
