package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class DriveForward extends Command {

	private double distanceInTicks;

	private boolean done = false;

	private static final double MAX_SPEED = 0.9;

	private static final double DISTANCE_PER_TICK = 4 * Math.PI / 4000;

	private static final int TICKS_TIL_FULL = 16000;

	private int sign;

	private int frontLeftStart;
	private int frontRightStart;
	private int rearLeftStart;
	private int rearRightStart;

	public DriveForward(double distance) {
		sign = (int) (distance / Math.abs(distance));
		this.distanceInTicks = sign * distance / DISTANCE_PER_TICK;
		requires(Robot.drive);
	}

	protected void initialize() {
		frontLeftStart = Robot.drive.frontLeft.getEncPosition();
		frontRightStart = Robot.drive.frontRight.getEncPosition();
		rearLeftStart = Robot.drive.rearLeft.getEncPosition();
		rearRightStart = Robot.drive.rearRight.getEncPosition();
		done = false;
	}

	protected void execute() {
		Drive drive = Robot.drive;
		int changeFR = drive.frontRight.getEncPosition() - frontRightStart;
		int changeFL = drive.frontLeft.getEncPosition() - frontLeftStart;
		int changeRR = drive.rearRight.getEncPosition() - rearRightStart;
		int changeRL = drive.rearLeft.getEncPosition() - rearLeftStart;
		int average = sign * (changeFR + changeRR - changeFL - changeRL) / 4;
		double speed = sign * getSpeed(average);
		if (Math.abs(distanceInTicks - average) < 3000)
			quit();
		else {
			drive.mode = 2;
			drive.driveTrain.mecanumDrive_Cartesian(0,-speed, drive.turnController.getOutput(closeToLockAngle(Robot.drive.navx.getYaw()), drive.lockAngle), 0);
		}

		NetworkTable.getTable("encoders").putNumber("average", average);
		NetworkTable.getTable("encoders").putNumber("trySpeed", speed);
		NetworkTable.getTable("encoders").putNumber("distanceInTicks", distanceInTicks);
	}

	private double closeToLockAngle(double yaw) {
		double yaw2 = yaw + 360;
		double yaw3 = yaw - 360;
		if (Math.abs(yaw3 - Robot.drive.lockAngle) < Math.abs(yaw2 - Robot.drive.lockAngle)) {
			yaw2 = yaw3;
		}
		if (Math.abs(yaw2 - Robot.drive.lockAngle) < Math.abs(yaw - Robot.drive.lockAngle)) {
			yaw = yaw2;
		}
		return yaw;
	}

	private void quit() {
		Drive drive = Robot.drive;
		drive.frontLeft.set(0);
		drive.frontRight.set(0);
		drive.rearLeft.set(0);
		drive.rearRight.set(0);
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

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return done;
	}

	public boolean isRunning() {
		return !done;
	}

	protected void end() {}

	protected void interrupted() {}
}
