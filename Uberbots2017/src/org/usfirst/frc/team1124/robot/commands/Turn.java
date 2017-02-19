package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Turn extends Command {

	private boolean done = false;

	private static final double MAX_SPEED = 0.6;

	private static final double QUIT_SPEED = 0.3;
	public double degrees;

	public Turn(double degrees) {
		requires(Robot.drive);
		this.degrees = degrees;
	}

	protected void initialize() {
		Robot.drive.lockAngle -= degrees;
		Robot.drive.lockAngle = Robot.drive.lockAngle % 360;
		done = false;
	}

	protected void execute() {

		NetworkTable.getTable("debug").putNumber("test", 13);
		NetworkTable.getTable("debug").putNumber("rand", Math.random());
		NetworkTable.getTable("debug").putNumber("left",
				Robot.drive.ultrasonic1.getAverageVoltage() * Drive.ULTRASONIC_SCALE - 11);
		NetworkTable.getTable("debug").putNumber("right",
				Robot.drive.ultrasonic2.getAverageVoltage() * Drive.ULTRASONIC_SCALE - 11);
		if (Math.abs(Robot.drive.turnController.getOutput(closeToLockAngle(Robot.drive.navx.getYaw()),
				Robot.drive.lockAngle)) * MAX_SPEED < QUIT_SPEED)
			quit();
		else {
			Robot.drive.mode = 2;
			Robot.drive.driveTrain.mecanumDrive_Cartesian(0, 0, Robot.drive.turnController
					.getOutput(closeToLockAngle(Robot.drive.navx.getYaw()), Robot.drive.lockAngle) * MAX_SPEED, 0);
		}

		NetworkTable.getTable("turn").putNumber("angle", Robot.drive.navx.getYaw());

		NetworkTable.getTable("turn").putNumber("lockAngle", Robot.drive.lockAngle);
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

	protected boolean isFinished() {
		return done;
	}

	public boolean isRunning() {
		return !done;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
