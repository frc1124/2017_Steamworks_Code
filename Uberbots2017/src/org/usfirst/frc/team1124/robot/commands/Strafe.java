package org.usfirst.frc.team1124.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class Strafe extends Command {
	
	@SuppressWarnings("unused")
	private double distance;
	
	public Strafe(double distance){
		this.distance = distance;
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
