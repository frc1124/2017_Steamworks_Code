package org.usfirst.frc.team1124.robot.commands;

import org.opencv.core.Mat;
import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.vision.Camera;
import org.usfirst.frc.team1124.vision.GripPipeline;

import edu.wpi.first.wpilibj.command.Command;

public class TargetVisionTape extends Command {
	private boolean done = false;

	public static final int CAMERA_EXPOSURE = 3;

	private int tolerance = 2;
	double buffer = .4;

	private GripPipeline filter = null;
	private int backupMode = 2;

	public TargetVisionTape() {
		requires(Robot.camera);
		requires(Robot.drive);
		filter = new GripPipeline();

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
		Robot.drive.mode = 3;
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
		double mx = (lx + hx) / 2;
		double dfif = mx - (Camera.CAMERA_RES_X / 2);
		if (Math.abs(dfif) <= tolerance) {
			done = true;
			return;
		} else if (Math.abs(dfif) >= tolerance) {
			double strafe = dfif;
			if (strafe <= -1.0) {
				strafe = -1 * buffer;
			} // left
			else if (strafe >= 1.0) {
				strafe = 1 * buffer;
			} // right
			if (Math.abs(dfif) > tolerance)
				Robot.drive.run(strafe, 0);
			else
				done = true;
			return;
		}
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
