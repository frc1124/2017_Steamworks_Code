
package org.usfirst.frc.team1124.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.usfirst.frc.team1124.robot.subsystems.*;

import org.usfirst.frc.team1124.robot.commands.Teleop;

public class Robot extends IterativeRobot {
	public static Command teleop;
	public static OI oi;
	public static Drive drive;

	public void robotInit() {
		RobotMap.init();

		drive = new Drive();
		oi = new OI();
		teleop = new Teleop();
	}

	public void disabledInit() {}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {}

	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	public void testInit() {}

	public void testPeriodic() {}
}
