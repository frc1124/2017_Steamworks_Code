package org.usfirst.frc.team1124.robot;

import org.usfirst.frc.team1124.robot.subsystems.Climber;
import org.usfirst.frc.team1124.robot.subsystems.Drive;
import org.usfirst.frc.team1124.robot.subsystems.GearDoor;
import org.usfirst.frc.team1124.robot.auto.BeUseless;
import org.usfirst.frc.team1124.robot.auto.PlaceGearOnCenter;
import org.usfirst.frc.team1124.robot.auto.PlaceGearOnCenterAndLeft;
import org.usfirst.frc.team1124.robot.auto.PlaceGearOnCenterAndRight;
import org.usfirst.frc.team1124.robot.subsystems.Camera;
import org.usfirst.frc.team1124.robot.utils.StateManager;
import org.usfirst.frc.team1124.robot.utils.VisionComms;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Robot extends IterativeRobot {
	public static Drive chassis;
	public static Climber climber;
	public static GearDoor gearDoor;
	public static OI oi;
	public static Camera camera;
	public static Compressor compressor;
	public static Command auto;

	public void robotInit() {
		chassis = new Drive();
		climber = new Climber();
		gearDoor = new GearDoor();
		oi = new OI();
		camera = new Camera();
		
	}

	@SuppressWarnings("deprecation")
	public void autonomousInit() {
		switch(NetworkTable.getTable("dash").getInt("auto", 0)) {
			case 0: auto = new BeUseless(); break;
			case 1: auto = new PlaceGearOnCenter(); break;
			case 2: auto = new PlaceGearOnCenterAndLeft(); break;
			case 3: auto = new PlaceGearOnCenterAndRight(); break;
			case 4: break;
			default: auto = new BeUseless(); break;	
		}
		auto.start();
		StateManager.updateAll();
	}
	public void disabledInit() { Scheduler.getInstance().removeAll(); }
	public void teleopInit() {}
	public void disabledPeriodic() { Scheduler.getInstance().run(); }
	public void autonomousPeriodic() { Scheduler.getInstance().run(); }
	public void teleopPeriodic() {
		NetworkTable.getTable("debug").putNumber("leftUlt", Math.round(Drive.leftUltrasonic.getAverageVoltage()*1024*5)/5);
		NetworkTable.getTable("debug").putNumber("rightUlt", Math.round(Drive.rightUltrasonic.getAverageVoltage()*1024*5)/5);
		NetworkTable.getTable("debug").putNumber("visionVal", VisionComms.read());
		Scheduler.getInstance().run(); 
	}
	public void testPeriodic() {}
}
