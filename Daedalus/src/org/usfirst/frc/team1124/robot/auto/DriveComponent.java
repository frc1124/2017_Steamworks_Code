package org.usfirst.frc.team1124.robot.auto;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class DriveComponent extends Command {

	private double distanceInTicks;

	private boolean done = false;

	private static final double MAX_SPEED = 0.9;

	private static final double DISTANCE_PER_TICK = 4 * Math.PI / 4096;

	private static final int TICKS_TIL_FULL = 16000;

	private int sign;

	private int frontLeftStart;
	private int frontRightStart;
	private int rearLeftStart;
	private int rearRightStart;

	public DriveComponent(double distance) {
		sign = (int) (distance / Math.abs(distance));
		this.distanceInTicks = sign * distance / DISTANCE_PER_TICK;
		requires(Robot.chassis);
	}

	protected void initialize() {
		frontLeftStart = Drive.leftFront.getEncPosition();
		frontRightStart = Drive.rightFront.getEncPosition();
		rearLeftStart = Drive.leftBack.getEncPosition();
		rearRightStart = Drive.rightBack.getEncPosition();
		done = false;
	}

	protected void execute() {
		int changeFR = Drive.rightFront.getEncPosition() - frontRightStart;
		int changeFL = Drive.leftFront.getEncPosition() - frontLeftStart;
		int changeRR = Drive.rightBack.getEncPosition() - rearRightStart;
		int changeRL = Drive.leftBack.getEncPosition() - rearLeftStart;
		int average = sign * ((changeFR + changeRR) - (changeFL - changeRL)) / 4;
		double speed = sign * getSpeed(average);
		Drive drive = Robot.chassis;
		if (Math.abs(distanceInTicks - average) < 3000)
			quit();
		else {
			Drive.mec.mecanumDrive_Cartesian(0, -speed, drive.turnController.getOutput(closeToLockAngle(Drive.navx.getYaw()), Drive.lockAngle), 0);
		}

		NetworkTable.getTable("encoders").putNumber("average", average);
		NetworkTable.getTable("encoders").putNumber("trySpeed", speed);
		NetworkTable.getTable("encoders").putNumber("distanceInTicks", distanceInTicks);
	}

	private double closeToLockAngle(double yaw) {
		double yaw2 = yaw + 360;
		double yaw3 = yaw - 360;
		if (Math.abs(yaw3 - Drive.lockAngle) < Math.abs(yaw2 - Drive.lockAngle)) {
			yaw2 = yaw3;
		}
		if (Math.abs(yaw2 - Drive.lockAngle) < Math.abs(yaw - Drive.lockAngle)) {
			yaw = yaw2;
		}
		return yaw;
	}

	private void quit() {
		Drive.leftFront.set(0);
		Drive.rightFront.set(0);
		Drive.leftBack.set(0);
		Drive.rightBack.set(0);
		done = true;
	}

	private double getSpeed(int ticksSoFar) {
		if (ticksSoFar < distanceInTicks / 2) {
			if (ticksSoFar >= TICKS_TIL_FULL)
				return MAX_SPEED;
			else
				return Math.max(0.3, MAX_SPEED / TICKS_TIL_FULL * ticksSoFar);
		} else {
			if (ticksSoFar >= distanceInTicks - TICKS_TIL_FULL)
				return MAX_SPEED / TICKS_TIL_FULL * (distanceInTicks - ticksSoFar);
			else
				return MAX_SPEED;
		}
	}

	protected boolean isFinished() { return done; }

	public boolean isRunning() { return !done;}

	protected void end() {}

	protected void interrupted() { this.end(); }
}
