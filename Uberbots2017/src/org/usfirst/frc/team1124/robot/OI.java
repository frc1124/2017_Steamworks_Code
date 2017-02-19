package org.usfirst.frc.team1124.robot;



import org.usfirst.frc.team1124.robot.commands.Climb;
import org.usfirst.frc.team1124.robot.commands.DriveForward;
import org.usfirst.frc.team1124.robot.commands.PressToWin;
import org.usfirst.frc.team1124.robot.commands.ReverseClimb;
import org.usfirst.frc.team1124.robot.commands.TargetVisionTape;
import org.usfirst.frc.team1124.robot.commands.ToggleClimbDoor;
import org.usfirst.frc.team1124.robot.commands.ToggleGearDoor;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {

	// The Controllers
	public static Joystick stick = new Joystick(0);
	public static Joystick stickTwo = new Joystick(1);
	Button pressToWin = new JoystickButton(stick, 1);
	Button toggleGearDoor = new JoystickButton(stick, 2);
	Button toggleClimbDoor = new JoystickButton(stickTwo, 5);
	Button climbButton = new JoystickButton(stickTwo, 6);
	Button climbReverse = new JoystickButton(stickTwo, 8);

	public OI() {
		pressToWin.whileHeld(new PressToWin());
		toggleGearDoor.whenPressed(new ToggleGearDoor());
		toggleClimbDoor.whenPressed(new ToggleClimbDoor());
		climbButton.whileHeld(new Climb());
		climbReverse.whileHeld(new ReverseClimb());
	}
}
