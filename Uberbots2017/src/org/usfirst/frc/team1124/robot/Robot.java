package org.usfirst.frc.team1124.robot;

import org.usfirst.frc.team1124.robot.commands.DriveForward;
import org.usfirst.frc.team1124.robot.commands.Teleop;
import org.usfirst.frc.team1124.robot.commands.AutoTest;
import org.usfirst.frc.team1124.robot.commands.PIDTest;
import org.usfirst.frc.team1124.robot.commands.TeleopDrive;
import org.usfirst.frc.team1124.robot.subsystems.Drive;
import org.usfirst.frc.team1124.robot.subsystems.Pneumatics;
import org.usfirst.frc.team1124.robot.subsystems.DriveTest;
import org.usfirst.frc.team1124.vision.*;
import autoModules.DriveForward;
import org.usfirst.frc.team1124.vision.Camera;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {
//	public static Drive drive = new Drive();
	public static DriveTest drive = new DriveTest();
	public static OI oi = new OI();
	public static Command teleop = new Teleop();
//	public static DriveForward auto1 = new DriveForward(119);
	public static DriveForward auto1 = new AutoTest(3.0, 0.0, 0.0);
	public static DriveForward auto2 = new DriveForward(-119);

	public void robotInit() {
		drive.navx.reset();
		drive.navx.resetDisplacement();
		RobotMap.init();
	}

	public void disabledInit() {}

	public void autonomousInit() {
		auto1.start();
	}

	public void teleopInit() {}

	public void testInit() {}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousPeriodic() {
		if (auto1.done()) {
			auto1.cancel();
			//auto2.start();
		}
		//if (auto2.done())
		//	auto2.cancel();
		Scheduler.getInstance().run();
	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	public void testPeriodic() {
		Scheduler.getInstance().run();
	}
}
