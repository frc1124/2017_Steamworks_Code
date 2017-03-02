package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Strafe extends Command {
	
	private double time = 0;
	private double distance;
	private double curDistance = 0;
	
	private double speed = 0;
	
	private long deltaTime = 0;
	
	public Strafe(double d){
		
	}
	public Strafe() {
		
	}
	
	public Strafe(double distance, double speed){
		this.distance = distance;
		this.speed = speed;
		requires(Robot.drive);
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
		Robot.drive.mode = 2;
		Robot.drive.run(Robot.drive.strafeDist, 0);
		curDistance += Robot.speedX() * deltaTime;
		deltaTime = System.nanoTime() - time1;
		
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
}
