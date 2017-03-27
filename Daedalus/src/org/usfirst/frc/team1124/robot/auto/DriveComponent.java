package org.usfirst.frc.team1124.robot.auto;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class DriveComponent extends Command {

	private static final double MAX_SPEED = 0.9;
	private static final double DISTANCE_PER_TICK = 4 * Math.PI / 4096;
	private static final int TICKS_TIL_FULL = 16000;
	private static final int QUIT_DISTANCE = 1000;

	// Distance we need to move in encoder ticks
	private double distanceInTicks;

	// To know if we supposed to be moving backwards or forwards
	private int direction;

	// Used to see how much we have moved
	private int frontLeftStart;
	private int frontRightStart;
	private int rearLeftStart;
	private int rearRightStart;

	private boolean done = false;

	public DriveComponent(double distance) {
		super(Math.abs(distance) / 12d);
		direction = (int) Math.signum(distance);
		this.distanceInTicks = direction * distance / DISTANCE_PER_TICK;
		requires(Robot.chassis);
	}

	@Override
	protected void initialize() {
		// Setting where we started
		frontLeftStart = Drive.leftFront.getEncPosition();
		frontRightStart = Drive.rightFront.getEncPosition();
		rearLeftStart = Drive.leftBack.getEncPosition();
		rearRightStart = Drive.rightBack.getEncPosition();
		done = false;
	}

	@Override
	protected void execute() {
		int distTraveled = getDistTraveled();
		double speed = direction * getSpeed(distTraveled);

		Drive drive = Robot.chassis;

		//If we are close enough to the target distance
		if (Math.abs(distanceInTicks - distTraveled) < QUIT_DISTANCE)
			quit();
		else {
			Drive.mec.mecanumDrive_Cartesian(0, -speed, drive.turnController.getOutput(closeToLockAngle(Drive.navx.getYaw()), Drive.lockAngle), 0);
		}

		// Debug
		NetworkTable.getTable("encoders").putNumber("distanceTraveled", distTraveled);
		NetworkTable.getTable("encoders").putNumber("trySpeed", speed);
		NetworkTable.getTable("encoders").putNumber("distanceInTicks", distanceInTicks);
	}

	private int getDistTraveled() {
		// We can take advantage of the fact that we will only be driving forward of backwards
		int changeFR = Drive.rightFront.getEncPosition() - frontRightStart;
		int changeFL = Drive.leftFront.getEncPosition() - frontLeftStart;
		int changeRR = Drive.rightBack.getEncPosition() - rearRightStart;
		int changeRL = Drive.leftBack.getEncPosition() - rearLeftStart;
		int average = direction * ((changeFR + changeRR) - (changeFL - changeRL)) / 4;
		return average;
	}

	// Making sure that we use a lock angle that is numerically closest to the wanted angle
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

	// Stop all wheels
	private void quit() {
		Drive.leftFront.set(0);
		Drive.rightFront.set(0);
		Drive.leftBack.set(0);
		Drive.rightBack.set(0);
		done = true;
	}

	// Get the speed we want to move at based on where we are between the start and target
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

	protected boolean isFinished() {
		// Are we done?
		return done;
	}

	@Override
	public boolean isRunning() {
		// We need this method to override 
		return !done;
	}

	@Override
	protected void end() {
		// We need this method to override 
		// Bad things happen if this method is not here
	}

	@Override
	protected void interrupted() {
		System.out.println("done");
	}
}
