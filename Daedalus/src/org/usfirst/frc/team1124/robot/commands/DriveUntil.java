package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

public class DriveUntil extends Command{

	private double distance = 0;
	
	public DriveUntil(double distance){
		super(1);
		this.distance = distance;
	}
	
	
	
	@Override
	protected void execute() {
		super.execute();
		Drive.leftFront.set(0.4);
		Drive.rightFront.set(0.4);
		Drive.leftBack.set(0.4);
		Drive.rightBack.set(0.4);
	}



	@Override
	protected void end() {
		super.end();
		Drive.leftFront.set(0);
		Drive.rightFront.set(0);
		Drive.leftBack.set(0);
		Drive.rightBack.set(0);
	}



	@Override
	protected boolean isFinished() {
		return Math.round(Drive.leftUltrasonic.getVoltage()*1024/5)*5 <= distance || Math.round(Drive.rightUltrasonic.getVoltage()*1024/5)*5 <= distance;
	}

}
