package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class Strafe extends Command {
	private double spd;

    public Strafe(double spd) { 
    	this.spd = spd;
    	this.requires(Robot.chassis); 
    }

    protected void initialize() {}
    protected void execute() { Robot.chassis.strafe(spd); }
    protected boolean isFinished() { return(false); }
    protected void end() { Robot.chassis.allStop(); }
    protected void interrupted() {
    	this.cancel();
    	this.end();
    }
}
