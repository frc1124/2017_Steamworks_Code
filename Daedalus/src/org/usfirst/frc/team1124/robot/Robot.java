package org.usfirst.frc.team1124.robot;

import org.usfirst.frc.team1124.robot.auto.BeUseless;
import org.usfirst.frc.team1124.robot.auto.PlaceGearOnCenter;
import org.usfirst.frc.team1124.robot.auto.PlaceGearOnCenterAndLeft;
import org.usfirst.frc.team1124.robot.auto.PlaceGearOnCenterAndRight;
import org.usfirst.frc.team1124.robot.auto.PlaceGearOnLeft;
import org.usfirst.frc.team1124.robot.auto.PlaceGearOnRight;
import org.usfirst.frc.team1124.robot.commands.Strafe;
import org.usfirst.frc.team1124.robot.subsystems.Camera;
import org.usfirst.frc.team1124.robot.subsystems.Climber;
import org.usfirst.frc.team1124.robot.subsystems.Drive;
import org.usfirst.frc.team1124.robot.subsystems.GearDoor;
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

	@SuppressWarnings("deprecation")
	public void robotInit() {
		chassis = new Drive();
		climber = new Climber();
		gearDoor = new GearDoor();
		oi = new OI();
		camera = new Camera();
		
		/*temporary*/
		NetworkTable.getTable("dash").putInt("auto", 4);
	}

	@SuppressWarnings("deprecation")
	public void autonomousInit() {
		switch(NetworkTable.getTable("dash").getInt("auto", 0)) {
			case 0: auto = new BeUseless(); break;
			case 1: auto = new PlaceGearOnCenter(); break;
			case 2: auto = new PlaceGearOnCenterAndLeft(); break;
			case 3: auto = new PlaceGearOnCenterAndRight(); break;
			case 4: auto = new PlaceGearOnLeft(); break;
			case 5: auto = new PlaceGearOnRight(); break;
			default: auto = new BeUseless(); break;	
		}
		auto.start();
		StateManager.updateAll();
	}
	public void disabledInit() { Scheduler.getInstance().removeAll(); }
	@SuppressWarnings("deprecation")
	public void teleopInit() {  }
	public void disabledPeriodic() { Scheduler.getInstance().run(); }
	public void autonomousPeriodic() { Scheduler.getInstance().run(); }
	public void teleopPeriodic() {
		NetworkTable.getTable("debug").putNumber("leftUlt", Math.round(Drive.leftUltrasonic.getAverageVoltage()*1024/5)*5);
		NetworkTable.getTable("debug").putNumber("rightUlt", Math.round(Drive.rightUltrasonic.getAverageVoltage()*1024/5)*5);
		NetworkTable.getTable("debug").putNumber("visionVal", VisionComms.read());

		NetworkTable.getTable("vision").putBoolean("sign", VisionComms.read(0));
		NetworkTable.getTable("vision").putBoolean("one", VisionComms.read(1));
		NetworkTable.getTable("vision").putBoolean("two", VisionComms.read(2));
		NetworkTable.getTable("vision").putBoolean("three", VisionComms.read(3));
		NetworkTable.getTable("vision").putBoolean("four", VisionComms.read(4));
		NetworkTable.getTable("vision").putBoolean("five", VisionComms.read(5));
		
		NetworkTable.getTable("limit").putBoolean("limit", climber.limit.get());
		Scheduler.getInstance().run(); 
	}
	public void testInit() {}
	public void testPeriodic() { Scheduler.getInstance().run(); }
}
