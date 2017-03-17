package org.usfirst.frc.team1124.robot;

import org.usfirst.frc.team1124.robot.subsystems.Climber;
import org.usfirst.frc.team1124.robot.subsystems.Drive;
import org.usfirst.frc.team1124.robot.subsystems.Camera;
import org.usfirst.frc.team1124.robot.utils.StateManager;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Robot extends IterativeRobot {
	public static Drive chassis;
	public static Climber climber;
	public static OI oi;
	public static Camera camera;
	public static Compressor compressor;

	public void robotInit() {
		chassis = new Drive();
		climber = new Climber();
		oi = new OI();
		camera = new Camera();
		
	}

	@SuppressWarnings("deprecation")
	public void autonomousInit() {
		switch(NetworkTable.getTable("dash").getInt("auto", 0)) {
			case 0: break;
			case 1: break;
			case 2: break;
			default: break;	
		}
		StateManager.updateAll();
	}
	public void disabledInit() { Scheduler.getInstance().removeAll(); }
	public void teleopInit() {}
	public void disabledPeriodic() { Scheduler.getInstance().run(); }
	public void autonomousPeriodic() { Scheduler.getInstance().run(); }
	public void teleopPeriodic() { Scheduler.getInstance().run(); }
	public void testPeriodic() {}
}
