package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class DriveForward extends Command {

	private double distanceInTicks;

	private boolean done = false;

	private static final double MAX_SPEED = 0.7;

	private static final double DISTANCE_PER_TICK = 4 * Math.PI / 4000;

	private static final int TICKS_TIL_FULL = 16000;

	private static final double QUIT_SPEED = 0.1;

	private int sign;

	public DriveForward(double distance) {
		sign = (int) (distance / Math.abs(distance));
		this.distanceInTicks = sign * distance / DISTANCE_PER_TICK;
		requires(Robot.drive);
	}

	protected void initialize() {
		Robot.drive.rearRight.setEncPosition(0);
		Robot.drive.rearLeft.setEncPosition(0);
		Robot.drive.frontRight.setEncPosition(0);
		Robot.drive.frontLeft.setEncPosition(0);
		done = false;
	}

	protected void execute() {
		Drive drive = Robot.drive;
		int average = sign * (-drive.frontLeft.getEncPosition() + drive.frontRight.getEncPosition() - drive.rearLeft.getEncPosition() + drive.rearRight.getEncPosition()) / 4;
		double speed = sign * getSpeed(average);
		if (Math.abs(speed) <= QUIT_SPEED)
			quit();
		else {
			Robot.drive.mode = 2;
			Robot.drive.run(0, speed);
		}

		NetworkTable.getTable("encoders").putNumber("frontLeft", drive.frontLeft.getEncPosition());
		NetworkTable.getTable("encoders").putNumber("frontRight", drive.frontRight.getEncPosition());
		NetworkTable.getTable("encoders").putNumber("rearLeft", drive.rearLeft.getEncPosition());
		NetworkTable.getTable("encoders").putNumber("rearRight", drive.rearRight.getEncPosition());
		NetworkTable.getTable("ultrasonic").putNumber("u1", drive.ultrasonic1.getAverageVoltage());
		NetworkTable.getTable("ultrasonic").putNumber("u2", drive.ultrasonic2.getAverageVoltage());
		NetworkTable.getTable("encoders").putNumber("average", average);
		NetworkTable.getTable("encoders").putNumber("needed", distanceInTicks);
		NetworkTable.getTable("encoders").putNumber("trySpeed", speed);
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

	public boolean done() {
		return done;
	}

	// Called once after isFinished returns true
	protected void end() {}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {}
}
