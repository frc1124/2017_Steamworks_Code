
package org.usfirst.frc.team1124.robot;

import org.usfirst.frc.team1124.robot.commands.TeleopDrive;
import org.usfirst.frc.team1124.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {
	public static TeleopDrive teleopDrive;
	public static OI oi;
	public static Drive drive;
	
	
	

	public void robotInit() {
		RobotMap.init();
		drive = new Drive();
		oi = new OI();
		teleopDrive = new TeleopDrive();
		while(drive.navX.getYaw() >= 0.3f) drive.navX.zeroYaw();
		
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

	public void testPeriodic() {}
}
