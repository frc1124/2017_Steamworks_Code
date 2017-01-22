
package org.usfirst.frc.team1124.robot;

import org.usfirst.frc.team1124.robot.subsystems.Drive;
import org.usfirst.frc.team1124.vision.*;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;


public class Robot extends IterativeRobot {
	public static Command teleopDrive;
	public static OI oi;
	public static Drive drive;
	public static Camera camera1;
	public void robotInit() {
		RobotMap.init();

		drive = new Drive();
		oi = new OI();
		camera1 = new Camera();
	}

	public void disabledInit() {}

	@SuppressWarnings("deprecation")
	public void autonomousInit() {
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

	public void testPeriodic() {}
}
