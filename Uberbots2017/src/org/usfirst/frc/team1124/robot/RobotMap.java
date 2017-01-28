package org.usfirst.frc.team1124.robot;

public class RobotMap {

	public static final int FRONT_LEFT = 1;
	public static final int REAR_LEFT = 2;
	public static final int FRONT_RIGHT = 3;
	public static final int REAR_RIGHT = 4;

	public static final boolean[] INVERTED = new boolean[] { false, true, true, false, false };
	public static final int TURN_PID = 0;
	public static final int TRANS_PID = 5;
	public static final double[] P = new double[] { 0.03, 1, 1, 1, 1, 0.6 };
	public static final double[] I = new double[] { 0.001, 0, 0, 0, 0, 0 };
	public static final double[] D = new double[] { 0.1, 0.1, 0.1, 0.1, 0.1, 0.1 };

	public static void init() {}
}
