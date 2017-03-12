package org.usfirst.frc.team1124.robot;

import org.usfirst.frc.team1124.robot.commands.ArcadeDrive;
import org.usfirst.frc.team1124.robot.commands.TankDrive;
import org.usfirst.frc.team1124.robot.subsystems.Drive;
import org.usfirst.frc.team1124.robot.utils.StateManager;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Robot extends IterativeRobot {
	//subsystems
	public static Drive chassis;
	//commands
	public static Command tankDrive;
	public static Command arcadeDrive;
	//misc
	public static OI oi;

	public void robotInit() {
		chassis = new Drive();
		tankDrive = new TankDrive();
		arcadeDrive = new ArcadeDrive();
		oi = new OI();
	}

	@SuppressWarnings("deprecation")
	public void autonomousInit() {
		switch(NetworkTable.getTable("dash").getInt("auto", 0)) {
			case 0: break;
			case 1: break;
			case 2: break;
			default: break;	
		}
		StateManager.updateArduino();
	}
	public void disabledInit() { Scheduler.getInstance().removeAll(); }
	public void teleopInit() {}
	public void disabledPeriodic() { Scheduler.getInstance().run(); }
	public void autonomousPeriodic() { Scheduler.getInstance().run(); }
	public void teleopPeriodic() { Scheduler.getInstance().run(); }
	public void testPeriodic() {}
}
