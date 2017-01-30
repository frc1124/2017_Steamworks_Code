package org.usfirst.frc.team1124.robot;

import org.usfirst.frc.team1124.robot.commands.TeleopDrive;
import org.usfirst.frc.team1124.robot.subsystems.Drive;
import org.usfirst.frc.team1124.vision.Camera;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {

	// Operator Interface
	public static OI oi;

	// Subsystems
	public static Drive drive;
	public static Camera camera1;

	// Commands
	public static TeleopDrive teleopDrive;

	public void robotInit() {

		oi = new OI();

		drive = new Drive();
		camera1 = new Camera();

		teleopDrive = new TeleopDrive();

		// reseting the NavX
		drive.getNavx().reset();
		drive.getNavx().zeroYaw();
	}

	public void disabledInit() {}

	public void autonomousInit() {}

	public void teleopInit() {}

	public void testInit() {}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	public void testPeriodic() {
		Scheduler.getInstance().run();
	}
}
