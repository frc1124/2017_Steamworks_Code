package org.usfirst.frc.team1124.robot.auto;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class TurnComponent extends Command {

	private boolean done = false;

	private static final double MAX_SPEED = 0.6;
	private static final double QUIT_SPEED = 0.3;

	public double turnAmount; // In degrees

	public TurnComponent(double degrees) {
		requires(Robot.chassis);
		turnAmount = degrees;
	}

	@Override
	protected void initialize() {
		// Telling the drive train what angle we want
		Drive.lockAngle -= turnAmount;
		Drive.lockAngle = Drive.lockAngle % 360;
		done = false;
	}

	@Override
	protected void execute() {
		// If we are going so slow we are not even moving
		if (Math.abs(Robot.chassis.turnController.getOutput(closeToLockAngle(Drive.navx.getYaw()), Drive.lockAngle)) * MAX_SPEED < QUIT_SPEED)
			quit();
		else
			// Take advantage of the turn correction
			Drive.mec.mecanumDrive_Cartesian(0, 0, Robot.chassis.turnController.getOutput(closeToLockAngle(Drive.navx.getYaw()), Drive.lockAngle) * MAX_SPEED, 0);

		// Debug
		NetworkTable.getTable("turn").putNumber("angle", Drive.navx.getYaw());
		NetworkTable.getTable("turn").putNumber("lockAngle", Drive.lockAngle);
		NetworkTable.getTable("debug").putNumber("test", 13);
		NetworkTable.getTable("debug").putNumber("rand", Math.random());
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

	// Stopping the wheels
	private void quit() {
		Drive.leftFront.set(0);
		Drive.rightFront.set(0);
		Drive.leftBack.set(0);
		Drive.rightBack.set(0);
		done = true;
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
		// We need this method to override 
		// Bad things happen if this method is not here
	}
}
