package org.usfirst.frc.team1124.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

public class OI {
	public static Joystick stick = new Joystick(0);
	public static Button reset = new JoystickButton(stick, 8);

	public static double LeftX;
	public static double LeftY;
	public static double RightX;
	public static double RightY;

	public OI() {
		reset.whenPressed(new Command() {

			protected void initialize() {
				
				while(Math.abs(Robot.drive.navX.getYaw()) >= 0.3f)Robot.drive.navX.zeroYaw();
			}
			
			protected void execute() {
				while(Math.abs(Robot.drive.navX.getYaw()) >= 0.3f)Robot.drive.navX.zeroYaw();
			}

			@Override
			protected boolean isFinished() {
				return true;
			}

		});
	}
}
