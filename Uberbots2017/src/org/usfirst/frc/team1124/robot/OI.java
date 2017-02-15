package org.usfirst.frc.team1124.robot;


import org.usfirst.frc.team1124.robot.commands.Climb;
import org.usfirst.frc.team1124.robot.commands.PressToWin;
import org.usfirst.frc.team1124.robot.commands.ToggleClimbDoor;
import org.usfirst.frc.team1124.robot.commands.ToggleGearDoor;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {

	// The Controller
	public static Joystick stick = new Joystick(0);
	Button pressToWin = new JoystickButton(stick, 1);
	Button toggleGearDoor = new JoystickButton(stick, 2);
	Button toggleClimbDoor = new JoystickButton(stick, 5);
	Button climbButton = new JoystickButton(stick, 6);

	public OI() {
		pressToWin.whenPressed(new PressToWin());
		toggleGearDoor.whenPressed(new ToggleGearDoor());
		toggleClimbDoor.whenPressed(new ToggleClimbDoor());
		climbButton.whileHeld(new Climb());
	}

}
