package org.usfirst.frc.team1124.robot.commands;

import org.opencv.core.Mat;
import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.vision.Camera;
import org.usfirst.frc.team1124.vision.GripPipeline;
import edu.wpi.first.wpilibj.command.Command;

public class TargetVisionTape extends Command {
	private boolean done = false;

	private double VERTICAL_PIXEL_HEIGHT = 80;
	private double MAX_HORIZONTAL_DISTANCE = 18; // inches
	private double MAX_SPEED = 0.45;

	private double HORIZONTAL_PER_PIXEL = MAX_HORIZONTAL_DISTANCE / 320; // middle of the robot off center when vision tape is at right edge

	private double strafeDistance;
	private int initEncFl;
	private int initEncFr;
	private int initEncRl;
	private int initEncRr;

	private int backupMode = 2;

	public TargetVisionTape() {
		requires(Robot.camera);
		requires(Robot.drive);

	}

	protected void end() {
		Robot.drive.frontLeft.set(0);
		Robot.drive.frontRight.set(0);
		Robot.drive.rearLeft.set(0);
		Robot.drive.rearRight.set(0);
		this.done = true;
		Robot.drive.mode = backupMode;
	}

	protected void initialize() {
		backupMode = Robot.drive.mode;
		Robot.drive.mode = 2;
		Robot.drive.lockAngle = Robot.drive.navx.getYaw();
		done = false;

		// Get distance from vision
		Mat img = Robot.camera.getMat();
		GripPipeline filter = new GripPipeline();
		filter.process(img);
		double[] range = filter.getXRange();
		double lx, hx;
		lx = range[0];
		hx = range[1];
		double mx = (lx + hx) / 2;
		double dfif = mx - (Camera.CAMERA_RES_X / 2);
		if (lx == -1 || hx == -1) {
			this.strafeDistance = 0;
			return;
		}

		// Calculate a scaler based on how tall the vertical image is
		double[] height = filter.getYRange();
		double h = height[1] - height[0];
		double verticalScale = VERTICAL_PIXEL_HEIGHT / h;

		// Calculate distance to strafe
		this.strafeDistance = dfif * HORIZONTAL_PER_PIXEL * verticalScale;

		// Find initialial encoder positions
		this.initEncFl = Robot.drive.frontLeft.getEncPosition();
		this.initEncFr = Robot.drive.frontRight.getEncPosition();
		this.initEncRl = Robot.drive.rearLeft.getEncPosition();
		this.initEncRr = Robot.drive.rearRight.getEncPosition();

	} // mec mode

	protected void execute() {
		// If there is no strafe distance, return immediately
		if (this.strafeDistance == 0) {
			done = true;
			return;
		}

		// Get the average difference in encoders
		int dfl = Robot.drive.frontLeft.getEncPosition() - this.initEncFl;
		int dfr = this.initEncFr - Robot.drive.frontRight.getEncPosition(); // swap because we're strafing
		int drl = this.initEncRl - Robot.drive.frontRight.getEncPosition(); // swap because we're strafing
		int drr = Robot.drive.rearRight.getEncPosition() - this.initEncRr;
		int ave = (dfl + dfr + drl + drr)/4;

		// Calculate the distance in inches
		double distance = (ave * 4 * Math.PI) / 4096;

		// If we're at or overshot, finish
		if (this.strafeDistance > 0 && distance >= this.strafeDistance) {
			Robot.drive.run(0,0);
			done = true;
			return;
		}
		if (this.strafeDistance < 0 && distance <= this.strafeDistance) {
			Robot.drive.run(0,0);
			done = true;
			return;
		}

		// Run the strafe
		Robot.drive.run(this.getSpeed(distance),0);
	}

	private double getSpeed(double distance) {
		double aDistance = Math.abs(distance);
		double aStrafeDistance = Math.abs(this.strafeDistance);
		if (aDistance >= aStrafeDistance) {
			return 0.0;
		}

		// Calculate the speed
		double maxAccelDistance = 6;
		if (aStrafeDistance < 12) {
			maxAccelDistance = aStrafeDistance / 2;
		}

		// Ramp up speed at beginning
		if (aDistance < maxAccelDistance) {
			return Math.signum(this.strafeDistance) * (aDistance * MAX_SPEED) / maxAccelDistance;
		}

		// Ramp down speed at end
		if (aDistance > aStrafeDistance - maxAccelDistance) {
			return Math.signum(this.strafeDistance) * ((aStrafeDistance - aDistance) * MAX_SPEED) / maxAccelDistance;
		}

		// Maintain max speed in middle
		return Math.signum(this.strafeDistance) * MAX_SPEED;
	}

	public void interrupted() {
		Robot.drive.frontLeft.set(0);
		Robot.drive.frontRight.set(0);
		Robot.drive.rearLeft.set(0);
		Robot.drive.rearRight.set(0);
		this.done = true;
	}

	@Override
	protected boolean isFinished() {
		return done;
	}

}
