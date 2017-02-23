package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.subsystems.Drive;
import edu.wpi.first.wpilibj.command.Command;

public class Strafe extends Command {
	
	@SuppressWarnings("unused")
	private double distance;
	private double distanceInTicks;
	private double distanceX;
	private double distanceInTicksX;
	private double distanceY;
	private double distanceInTicksY;

	private static final double MAX_SPEED = 0.9;
	private static final double DISTANCE_PER_TICK = 4 * Math.PI / 4096;

	private boolean done = false;
	private int sign;

	private int frontLeftStart;
	private int frontRightStart;
	private int rearLeftStart;
	private int rearRightStart;

	private double accerlationSlope = 0.25/4096;
	private int backupMode;

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

		// Lock the angle
		Robot.drive.lockAngle();

		// Figure out the distanceX, distanceInTicksX, distanceY, and distanceInTicksY
		distanceX = distanceX * Math.cos(Robot.drive.lockAngle);
		distanceY = -1 * distanceY * Math.sin(Robot.drive.lockAngle);
		distanceInTicksX = distanceInTicksX * Math.cos(Robot.drive.lockAngle);
		distanceInTicksY = -1 * distanceInTicksY * Math.sin(Robot.drive.lockAngle);

		this.backupMode = Robot.drive.mode;
		Robot.drive.mode = 2;
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
		int changeFR = drive.frontRight.getEncPosition() - frontRightStart;
		int changeFL = drive.frontLeft.getEncPosition() - frontLeftStart;
		int changeRR = drive.rearRight.getEncPosition() - rearRightStart;
		int changeRL = drive.rearLeft.getEncPosition() - rearLeftStart;

		// Find the averageX and averageY over all four wheels
		double sinCos45 = Math.sin(45);
		double changeFLX = -1 * sinCos45 * changeFL; // forward motion produces negative X force
		double changeFLY = sinCos45 * changeFL;
		double changeFRX = sinCos45 * changeFR;
		double changeFRY = sinCos45 * changeFR;
		double changeRLX = sinCos45 * changeRL;
		double changeRLY = sinCos45 * changeRL;
		double changeRRX = -1 * sinCos45 * changeRR; // forward motion produces negative X force
		double changeRRY = sinCos45 * changeRR;

		// Find the average change over all wheels
		double averageX = sign * (changeFRX + changeRRX + changeFLX + changeRLX) / 4;
		double averageY = -1 * sign * (changeFRY + changeRRY + changeFLY + changeRLY) / 4;
		int ticksSoFar = (int)Math.sqrt(averageX * averageX + averageY * averageY);

		// If we're within 325 ticks (1 inch), finish
		if (Math.abs(distanceInTicks - ticksSoFar) < 325) {
			end();
			return;
		}

		// Calculate the speed based on the average and direction
		double speed = sign * getSpeed(ticksSoFar);
		double speedX = sign * speed * Math.cos(Robot.drive.lockAngle);
		double speedY = -1 * sign * speed * Math.sin(Robot.drive.lockAngle);

		// Strafe using mecanum
		Robot.drive.run(speedX,speedY);
	}

	@SuppressWarnings("unused")
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

	protected void end() {
		// Turn off motors
		Drive drive = Robot.drive;
		drive.frontLeft.set(0);
		drive.frontRight.set(0);
		drive.rearLeft.set(0);
		drive.rearRight.set(0);
		drive.mode = backupMode;
		done = true;
	}

	private double getSpeed(int ticksSoFar) {
		// Use the difference from the end to calculate the speed
		double speed = accerlationSlope * Math.abs(distanceInTicks - ticksSoFar);

		// Cap the speed
		if (speed > MAX_SPEED) {
			speed = MAX_SPEED;
		}
		return speed;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return done;
	}

	public boolean isRunning() {
		return !done;
	}

	protected void interrupted() { this.end(); }
}
