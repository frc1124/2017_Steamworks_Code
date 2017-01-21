package org.usfirst.frc.team1124.robot.commands;

import org.opencv.core.Mat;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.vision.GripPipeline;

import edu.wpi.first.wpilibj.command.Command;

public class TargetVisionTape extends Command{
	private boolean done = false;
	
	private GripPipeline filter = null;
	
	public TargetVisionTape(){
		requires(Robot.camera1);
		requires(Robot.drive);
		filter = new GripPipeline();
		
	}
	
	protected void execute(){
		Mat img = Robot.camera1.getMat();
		this.filter.process(img);
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return done;
	}

}
