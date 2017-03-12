package org.usfirst.frc.team1124.robot.utils;

import org.usfirst.frc.team1124.robot.RobotMap;

public class StateManager {
	//dashboard vars
	public static double matchTime = 0;
	public static String cameraFeed = "";
	public static String name = RobotMap.name;
	public static double speed = 0;
	
	//table vars
	public static double leftFrontEnc = 0;
	public static double leftBackEnc = 0;
	public static double rightFrontEnc = 0;
	public static double rightBAckEnc = 0;
	public static double gyroAngle = 0;
	public static double leftUltrasonic = 0;
	public static double rightUltrasonic = 0;
	public static double gearUltrasonic = 0;
	
	//arduino vars
	public static double robotState = 0;
	
	public static void updateDashboard() {}
	public static void updateArduino() {}
	public static void updateTables() {}
	public static void updateAll() {
		updateDashboard();
		updateArduino();
		updateTables();
	}
}
