package org.usfirst.frc.team1124.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	public static Joystick stick = new Joystick(0);
	public static Button MechDir = new JoystickButton(stick, 8);

	public static double LeftX;
	public static double LeftY;
	public static double RightX;
	public static double RightY;

	public OI() {}
}
