package org.usfirst.frc.team1124.robot.commands;

import org.opencv.core.Mat;
import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.RobotMap;
import org.usfirst.frc.team1124.robot.subsystems.Drive;
import org.usfirst.frc.team1124.vision.Camera;
import org.usfirst.frc.team1124.vision.GripPipeline;

import edu.wpi.first.wpilibj.command.Command;

public class TargetVisionTape extends Command {
	private boolean done = false;

	private int tolerance = 5;
	
	double buffer = .3;

	private GripPipeline filter = null;

	public TargetVisionTape() {
		requires(Robot.camera1);
		requires(Robot.drive);
		filter = new GripPipeline();

	}
	protected void initilize() { Robot.drive.mode = 2; } //mec mode
 
	protected void execute() {

		Mat img = Robot.camera1.getMat();
		this.filter.process(img);
		double[] range = filter.getXRange();
		double lx, hx;
		lx = range[0];
		hx = range[1];
		double mx = (lx + hx) / 2;
		double dfif = mx - (Camera.CAMERA1_RES_X / 2);
		if (Math.abs(dfif) <= tolerance) {
			done = true;
			return;
		}
		double strafe = dfif;
		if (strafe <= -1.0) {
			strafe = -1 * buffer;
		} else if (strafe >= 1.0) {
			strafe = 1 * buffer;
		}
	
		Robot.drive.run(strafe, 0);

	}

	@Override
	protected boolean isFinished() {
		return done;
	}

}
