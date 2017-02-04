package org.usfirst.frc.team1124.robot;

import org.usfirst.frc.team1124.robot.commands.AutoQueue;
import org.usfirst.frc.team1124.robot.commands.DriveForward;
import org.usfirst.frc.team1124.robot.commands.Teleop;
import org.usfirst.frc.team1124.robot.commands.Turn;
import org.usfirst.frc.team1124.robot.subsystems.Drive;
import org.usfirst.frc.team1124.robot.subsystems.Pneumatics;
import org.usfirst.frc.team1124.vision.Camera;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {
	public static Drive drive;

	public static Pneumatics pneumatics;

	public static OI oi;

	public static Command teleop;

	public static AutoQueue auto;

	public static Camera camera;

	public void robotInit() {
		drive = new Drive();
		pneumatics = new Pneumatics();
		teleop = new Teleop();
		camera = new Camera();

		auto = new AutoQueue();
		drive.navx.reset();
		drive.navx.resetDisplacement();
		RobotMap.init();

		oi = new OI();
	}

	public void disabledInit() {}

	public void autonomousInit() {
		new AutoQueue().start();
	}

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