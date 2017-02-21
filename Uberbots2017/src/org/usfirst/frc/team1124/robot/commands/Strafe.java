package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Strafe extends Command {
	
	private double time = 0;
	private double distance;
	private double curDistance = 0;
	
	private double speed = 0.4;
	
	private long deltaTime = 0;
	
	public Strafe(double distance){
		this.distance = distance;
	}
	
	public Strafe(double distance, double speed){
		this.distance = distance;
		this.speed = speed;
	}

	@Override
	protected void interrupted(){
		super.interrupted();
	}
	
	@Override
	protected void initialize() {
		super.initialize();
	}

	@Override
	protected void execute() {
		long time1 = System.nanoTime();
		super.execute();
		curDistance += Robot.speedX() * deltaTime;
		deltaTime = System.nanoTime() - time1;
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return curDistance >= distance;
	}
}
