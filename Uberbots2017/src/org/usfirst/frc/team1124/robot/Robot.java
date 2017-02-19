package org.usfirst.frc.team1124.robot;

import org.usfirst.frc.team1124.robot.commands.AutoQueue;
import org.usfirst.frc.team1124.robot.commands.DriveForward;
import org.usfirst.frc.team1124.robot.commands.Teleop;
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
	public static Command teleop;
	public static AutoQueue auto;
	public static Camera camera;
	public static Command dumbAuto;
	public static Command noAuto;

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
	}

	public void disabledInit() {}
	public void autonomousInit() {
		
		int autoMode = ((int)NetworkTable.getTable("dash").getNumber("auto"));
		switch(autoMode) {
		case 0: noAuto.start(); System.out.println("no auto"); break;
		case 1: dumbAuto.start(); System.out.println("auto"); break;
		default: noAuto.start(); System.out.println("no auto default " + autoMode); break;
		}
	}
	public void teleopInit() {}
	public void testInit() {}

	public void disabledPeriodic() { Scheduler.getInstance().run(); }
	public void autonomousPeriodic() { Scheduler.getInstance().run(); }
	public void teleopPeriodic() { 
		NetworkTable.getTable("dash").putNumber("front_right_encoder", drive.frontRight.getEncPosition());
		NetworkTable.getTable("dash").putNumber("front_right_encoder", drive.frontRight.getEncPosition());
		NetworkTable.getTable("dash").putNumber("front_right_encoder", drive.frontRight.getEncPosition());
		NetworkTable.getTable("dash").putNumber("front_right_encoder", drive.frontRight.getEncPosition());
		Scheduler.getInstance().run();
	}
	public void testPeriodic() { Scheduler.getInstance().run(); }
}