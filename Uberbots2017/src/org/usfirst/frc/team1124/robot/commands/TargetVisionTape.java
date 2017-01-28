package org.usfirst.frc.team1124.robot.commands;

import org.opencv.core.Mat;
import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.RobotMap;
import org.usfirst.frc.team1124.vision.GripPipeline;

import edu.wpi.first.wpilibj.command.Command;

public class TargetVisionTape extends Command{
	private boolean done = false;
	
	private int tolerance = 5;
	
	private GripPipeline filter = null;
	
	public TargetVisionTape(){
		requires(Robot.camera1);
		requires(Robot.drive);
		filter = new GripPipeline();
		
	}
	
	protected void execute(){
		
		Mat img = Robot.camera1.getMat();
		this.filter.process(img);
		double[] range = filter.getXRange();
		double lx, hx;
		lx = range[0];
		hx = range[1];
		double mx = (lx + hx)/2;
		double dfif = mx - (RobotMap.CAMERA1_RES_X/2);
		if (Math.abs(dfif) <= tolerance){
			done = true;
			return;
		}
		double strafe = dfif;
		if (strafe <= -1.0){
			strafe = -1;
		}
		else if(strafe >= 1.0){
			strafe = 1;
		}
		Robot.drive.getDrive()
		.mecanumDrive_Cartesian(strafe, 0.0, 0.0, Robot.drive.getLockAngle()); 
		
	}
	
	@Override
	protected boolean isFinished() {
		Robot.drive.getDrive()
		.mecanumDrive_Cartesian(0.0, 0.0, 0.0, Robot.drive.getLockAngle()); 
		return done;
	}
	
}
