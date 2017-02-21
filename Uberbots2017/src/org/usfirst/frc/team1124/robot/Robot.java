package org.usfirst.frc.team1124.robot;

import java.util.Calendar;

import org.usfirst.frc.team1124.robot.commands.AutoQueue;
import org.usfirst.frc.team1124.robot.commands.DriveForward;
import org.usfirst.frc.team1124.robot.commands.Teleop;
import org.usfirst.frc.team1124.robot.commands.autonomous.*;
import org.usfirst.frc.team1124.robot.subsystems.Climber;
import org.usfirst.frc.team1124.robot.subsystems.Drive;
import org.usfirst.frc.team1124.robot.subsystems.GearDoor;
import org.usfirst.frc.team1124.vision.Camera;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Robot extends IterativeRobot {
	public static Drive drive;
	public static GearDoor gearDoor;
	public static Climber climber;
	public static OI oi;
	public static long lastTime;
	public static Command teleop;
	public static AutoQueue auto;
	public static Camera camera;
	public static Command dumbAuto;
	public static Command noAuto;
	
	//autos
	public static Command placeGearOnCenter;
	public static Command placeGearOnCenterAndLeft;
	public static Command placeGearOnCenterAndRight;
	public static Command placeGearOnLeft;
	public static Command placeGearOnRight;

	public void robotInit() {
		drive = new Drive();
		gearDoor = new GearDoor();
		climber = new Climber();
		teleop = new Teleop();
		camera = new Camera();

		auto = new AutoQueue();
		drive.navx.reset();
		drive.navx.resetDisplacement();
		dumbAuto = new DriveForward(96);
		noAuto = new DriveForward(0);
		RobotMap.init();

		oi = new OI();
		lastTime = Calendar.getInstance().getTimeInMillis();
		
		//autos
		placeGearOnCenter = new PlaceGearOnCenter();
		placeGearOnCenterAndLeft = new PlaceGearOnCenterAndLeft();
		placeGearOnCenterAndRight = new PlaceGearOnCenterAndRight();
		placeGearOnRight = new PlaceGearOnRight();
		placeGearOnLeft = new PlaceGearOnLeft();
		
	}

	public void disabledInit() {}
	@SuppressWarnings("deprecation")
	public void autonomousInit() {
		
		int autoMode = ((int)NetworkTable.getTable("dash").getNumber("auto"));
		switch(autoMode) {
		case 0: noAuto.start(); break;
		case 1: dumbAuto.start(); break;
		case 2: placeGearOnCenter.start(); break;
		case 3: placeGearOnCenterAndLeft.start(); break;
		case 4: placeGearOnCenterAndRight.start(); break;
		case 5: placeGearOnRight.start(); break;
		case 6: placeGearOnLeft.start(); break;
		default: noAuto.start(); break;
		}
	}
	public void teleopInit() {}
	public void testInit() {}

	public void disabledPeriodic() { Scheduler.getInstance().run(); }
	public void autonomousPeriodic() { Scheduler.getInstance().run(); }
	public void teleopPeriodic() { 
		updateDashboard();
		Scheduler.getInstance().run();

	}
	public void testPeriodic() { Scheduler.getInstance().run(); }
	
	public void updateDashboard() {
//		long curTime = Calendar.getInstance().getTimeInMillis();
//		long diff = curTime - lastTime;
//		lastTime = curTime;
//		double speed = Math.PI * 4;
		NetworkTable.getTable("dash").putBoolean("limit", climber.limit.getAverageVoltage()>2);
		double speed = (( Math.abs(drive.frontLeft.getSpeed()) + Math.abs(drive.frontRight.getSpeed() + Math.abs(drive.rearRight.getSpeed()) + Math.abs(drive.rearLeft.getSpeed())) )/4) * (4*Math.PI) / 60;
		NetworkTable.getTable("dash").putNumber("speed", speed);
		NetworkTable.getTable("dash").putNumber("us1", drive.ultrasonic1MM());
		NetworkTable.getTable("dash").putNumber("us2", drive.ultrasonic2MM());
	}
}