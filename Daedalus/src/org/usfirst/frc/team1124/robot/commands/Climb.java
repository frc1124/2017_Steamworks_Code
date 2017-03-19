package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class Climb extends Command {
	private boolean up;

    public Climb(boolean up) {
    	this.requires(Robot.climber);
    	this.up = up;
    }

    protected void initialize() {}
    protected void execute() { Robot.climber.limitOverride(up); }
    protected boolean isFinished() { return(false); }
    protected void end() { Robot.climber.allStop(); }
    protected void interrupted() { 
    	this.end();
    	this.cancel();
    }
}
