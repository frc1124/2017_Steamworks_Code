package org.usfirst.frc.team1124.robot.commands;

import org.opencv.core.Mat;
import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.vision.Camera;
import org.usfirst.frc.team1124.vision.GripPipeline;

import edu.wpi.first.wpilibj.command.Command;

public class TargetVisionTape extends Command {
	private final int TOLERANCE = 2;
	private final double MAX_SPEED = .4;

	private boolean done = false;
	private GripPipeline filter = null;
	private int backupMode = 2;

	public TargetVisionTape() {
		requires(Robot.camera);
		requires(Robot.drive);
		filter = new GripPipeline();
	}

	protected void initialize() {
		backupMode = Robot.drive.mode;
		Robot.drive.mode = 2;
		Robot.drive.lockAngle = Robot.drive.navx.getYaw();
		done = false;
	} // mec mode

	protected void execute() {

		Mat img = Robot.camera.getMat();
		this.filter.process(img);
		double[] range = filter.getXRange();
		double lx, hx;
		lx = range[0];
		hx = range[1];

		// If we can't see the vision tape, bail
		if (lx < 0 || hx < 0) {
			done = true;
			return;
		}

		// Find the middle
		double mx = (lx + hx) / 2;
		double dfif = mx - (Camera.CAMERA_RES_X / 2);
		if (Math.abs(dfif) <= TOLERANCE) {
			done = true;
			return;
		}

		// Figure out which direction to move in
		double strafe = getSpeed(dfif);

		// Split the speed into X and Y components
		double strafeX = strafe * Math.cos(Robot.drive.lockAngle);
		double strafeY = -1 * strafe * Math.sin(Robot.drive.lockAngle); // Multiply by -1 because lockAngle is at sin(-90) to direction
		Robot.drive.run(strafeX, strafeY);
	}

	private double getSpeed(double x) {
		double speed = MAX_SPEED * x / Camera.CAMERA_RES_X * 0.05;
		if (Math.abs(speed) > MAX_SPEED) {
			speed = (speed < 0.0) ? -1 * MAX_SPEED : MAX_SPEED;
		}

		// If x is a positive number of pixels from center (i.e. to the right), move left (negative speed)
		// If x is a negative number of pixels from center (i.e. to the left), move right (positive speed)
		return -1 * speed;
	}

	protected void end() {
		Robot.drive.frontLeft.set(0);
		Robot.drive.frontRight.set(0);
		Robot.drive.rearLeft.set(0);
		Robot.drive.rearRight.set(0);
		this.done = true;
		Robot.drive.mode = backupMode;
	}

	public void interrupted() {
		end();
	}

	@Override
	protected boolean isFinished() {
		return done;
	}

}
