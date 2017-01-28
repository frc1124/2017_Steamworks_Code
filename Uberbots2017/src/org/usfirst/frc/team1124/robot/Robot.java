package org.usfirst.frc.team1124.robot;

import org.usfirst.frc.team1124.robot.commands.PIDTest;
import org.usfirst.frc.team1124.robot.commands.TeleopDrive;
import org.usfirst.frc.team1124.robot.subsystems.Drive;
import org.usfirst.frc.team1124.vision.*;
import autoModules.DriveForward;
import org.usfirst.frc.team1124.vision.Camera;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {
	public static Drive drive;
	public static OI oi;
	public static Command teleopDrive;
	public static Command pidtest;
	public static Command autonomous;
	public static Camera camera1;

	public void robotInit() {
		RobotMap.init();

		drive = new Drive();
		oi = new OI();
		camera1 = new Camera();
		drive.getNavx().reset();
		drive.getNavx().zeroYaw();
		teleopDrive = new TeleopDrive();
		pidtest = new PIDTest();
		autonomous = new DriveForward(255);
		
	}

	public void disabledInit() {}
	public void autonomousInit() { autonomous.start(); }
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
