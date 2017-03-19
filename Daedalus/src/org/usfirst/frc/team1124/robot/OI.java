package org.usfirst.frc.team1124.robot;

import org.usfirst.frc.team1124.robot.commands.Climb;
import org.usfirst.frc.team1124.robot.commands.Strafe;
import org.usfirst.frc.team1124.robot.commands.ToggleGearDoor;
import org.usfirst.frc.team1124.robot.commands.ToggleRopeDoor;
import org.usfirst.frc.team1124.robot.utils.VisionComms;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	public static Joystick firstDriver = new Joystick(0);
	public static Joystick secondDriver = new Joystick(1);
	
	public static Button climb = new JoystickButton(secondDriver,RobotMap.secondDriverA);
	public static Button climbDown = new JoystickButton(secondDriver,RobotMap.secondDriverB);
	public static Button gearDoor = new JoystickButton(firstDriver,RobotMap.firstDriverB);
	public static Button ropeDoor = new JoystickButton(secondDriver,3);
	public static Button target = new JoystickButton(firstDriver,RobotMap.firstDriverA);
	public OI() {
		climb.whileHeld(new Climb(true));
		climbDown.whileHeld(new Climb(false));
		gearDoor.whenPressed(new ToggleGearDoor());
		ropeDoor.whenPressed(new ToggleRopeDoor());
		target.whileHeld(new Strafe(VisionComms.read()));
	}
}

