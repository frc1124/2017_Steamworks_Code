
package org.usfirst.frc.team1124.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.usfirst.frc.team1124.robot.subsystems.*;
import org.usfirst.frc.team1124.robot.commands.TeleopArcade;
import org.usfirst.frc.team1124.robot.commands.TeleopMech;

public class Robot extends IterativeRobot {
	public static Command teleopMech;
	public static Command teleopArcade;
	public static OI oi;
	public static Drive drive;

	public void robotInit() {
		RobotMap.init();

		drive = new Drive();
		oi = new OI();
		teleopMech = new TeleopMech();
		teleopArcade = new TeleopArcade();
	}

	public void disabledInit() {}

	public void autonomousInit() {}

	public void teleopInit() {}

	public void testInit() {}

	// Walden i swear to god...
	public void disabledPeriodic() {Scheduler.getInstance().run();}

	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	public void testPeriodic() {}
}
