package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.subsystems.Drive;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Strafe extends Command {
	
	@SuppressWarnings("unused")
	private double distance;
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
	
	public Strafe(double distance){
		// Store the actual distance to travel
		this.distance = distance;

		// Store the direction; negative is left
		sign = (int) (distance / Math.abs(distance));

		// Need to go twice as many ticks because the wheels are working at odds, diminishing in power by 1/2
		this.distanceInTicks = 2 * sign * distance / DISTANCE_PER_TICK;

		requires(Robot.drive);
	}

	protected void initialize() {
		// Store the initial encoder positions
		frontLeftStart = Robot.drive.frontLeft.getEncPosition();
		frontRightStart = Robot.drive.frontRight.getEncPosition();
		rearLeftStart = Robot.drive.rearLeft.getEncPosition();
		rearRightStart = Robot.drive.rearRight.getEncPosition();

		done = false;
	}

	protected void execute() {
		Drive drive = Robot.drive;

		/*
		 * going right:
		 * changeFL +
		 * changeRL -
		 * changeFR -
		 * changeRR +
		 * 
		 * going left:
		 * changeFL -
		 * changeRL +
		 * changeFR +
		 * changeRL -
		 * 
		 * sign left -
		 * sign right +
		 */

		// Get the offsets
		int changeFR = Math.abs(drive.frontRight.getEncPosition() - frontRightStart);
		int changeFL = Math.abs(drive.frontLeft.getEncPosition() - frontLeftStart);
		int changeRR = Math.abs(drive.rearRight.getEncPosition() - rearRightStart);
		int changeRL = Math.abs(drive.rearLeft.getEncPosition() - rearLeftStart);

		// Find the average change over all wheels
		int average = sign * (changeFR + changeRR + changeFL + changeRL) / 4;

		// Calculate the speed based on the average and direction
		double speed = sign * getSpeed(average);

		// If we're within 100 ticks (1 inch), finish
		if (Math.abs(distanceInTicks - average) < 325)
			quit();
		else {
			// Strafe using mecanum
			drive.mode = 2;
			drive.driveTrain.mecanumDrive_Cartesian(speed,0, drive.turnController.getOutput(closeToLockAngle(Robot.drive.navx.getYaw()), drive.lockAngle), 0);
		}

		NetworkTable.getTable("encoders").putNumber("average", average);
		NetworkTable.getTable("encoders").putNumber("trySpeed", speed);
		NetworkTable.getTable("encoders").putNumber("distanceInTicks", distanceInTicks);
	}

	private double closeToLockAngle(double yaw) {
		// Figure out the lock angle based on current yaw so we don't drift
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
		// Turn off motors
		Drive drive = Robot.drive;
		drive.frontLeft.set(0);
		drive.frontRight.set(0);
		drive.rearLeft.set(0);
		drive.rearRight.set(0);
		done = true;
	}

	private double getSpeed(int ticksSoFar) {
		// Figure out the speed based on how close to being done, maxing at MAX_SPEED
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

	protected void interrupted() { this.end(); }
}
