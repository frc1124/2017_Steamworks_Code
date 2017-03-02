package org.usfirst.frc.team1124.robot.commands;

import org.opencv.core.Mat;
import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.vision.Camera;
import org.usfirst.frc.team1124.vision.GripPipeline;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import utils.MiniPID;

public class TargetVisionTape extends Command {
	private MiniPID pid = new MiniPID(0.05,0,0.1);
	private boolean done = false;

	public static final int CAMERA_EXPOSURE = 3;

	private int tolerance = 10;
	double buffer = .4;

	private GripPipeline filter = null;
	private int backupMode = 2;
	
	private double[] range;
	private double lx, hx;
	double mx;
	double dfif;

	public TargetVisionTape() {
		//requires(Robot.camera);
		requires(Robot.drive);
		filter = new GripPipeline();
		pid.setOutputLimits(-0.6, 0.6);
		this.setInterruptible(false);
	}

	protected void end() {
//		Robot.drive.frontLeft.set(0);
//		Robot.drive.frontRight.set(0);
//		Robot.drive.rearLeft.set(0);
//		Robot.drive.rearRight.set(0);
		this.done = true;
//		Robot.drive.mode = backupMode;
	}

	protected void initialize() {
		backupMode = Robot.drive.mode;
		Robot.drive.mode = 2;
		Robot.drive.lockAngle = Robot.drive.navx.getYaw();
		done = false;
		
		Mat img = Robot.camera.getMat();
		this.filter.process(img);
		range = filter.getXRange();
		lx = range[0];
		hx = range[1];
		mx = (lx + hx) / 2;
		NetworkTable.getTable("dash").putNumber("centerLine", mx);
		dfif = mx - (Camera.CAMERA_RES_X / 2);
		
	} // mec mode

	protected void execute() {
//		Robot.drive.mode = 2;
//
		Mat img = Robot.camera.getMat();
		this.filter.process(img);
		range = filter.getXRange();
		lx = range[0];
		hx = range[1];
		mx = (lx + hx) / 2;
		NetworkTable.getTable("dash").putNumber("centerLine", mx);
		dfif = mx - (Camera.CAMERA_RES_X / 2);
		Robot.drive.strafeDist = Math.signum(dfif)*0.45;
//		if(Math.abs(dfif) > tolerance/2){
//			
//		}else{
//			//this.end();
//		}
//		if (Math.abs(dfif) <= tolerance) {
//			//done = true;
//			//return;
//		} else if (Math.abs(dfif) >= tolerance) {
//			double speed = 0.45*Math.signum(1);
//			Robot.drive.mode = 2;
//			Robot.drive.run(speed, 0);
//			Robot.drive.frontLeft.set(speed);
//			Robot.drive.frontRight.set(-speed);
//			Robot.drive.rearRight.set(speed);
//			Robot.drive.rearLeft.set(-speed);
		}
	//}
	
	public void interrupted() {
		this.end();
	}

	@Override
	protected boolean isFinished() {
		return done;
	}

}
