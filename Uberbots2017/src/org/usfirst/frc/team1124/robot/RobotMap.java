package org.usfirst.frc.team1124.robot;

public class RobotMap {

	// CANTalon IDs
	public static final int FRONT_LEFT = 1;
	public static final int REAR_LEFT = 2;
	public static final int FRONT_RIGHT = 3;
	public static final int REAR_RIGHT = 4;

	// Inversions for CANTalon
	public static final boolean[] INVERTED = new boolean[] { false, true, true, false, false };

	// PID indices for array
	public static final int TURN_PID = 0;
	public static final int TRANS_PID = 5;

	// P, I, and D values for PIDs
	public static final double[] P = new double[] { 0.03, 1, 0.1, 0.1, 1, 0.01 };
	public static final double[] I = new double[] { 0.001, 0, 0, 0, 0, 0 };
	public static final double[] D = new double[] { 0.1, 0.1, 0.1, 0.1, 0.1, 0 };

	// Camera Resolutions
	public static final int CAMERA1_RES_X = 640;
	public static final int CAMERA1_RES_Y = 480;

}
