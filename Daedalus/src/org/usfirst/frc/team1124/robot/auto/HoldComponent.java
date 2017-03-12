package org.usfirst.frc.team1124.robot.auto;

import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class HoldComponent extends Command {
	private long startTime;
	private long duration;

    public HoldComponent(long duration) {
    	this.requires(Robot.chassis);
    	this.startTime = 0;
    	this.duration = duration;
    }
    protected void initialize() { startTime = System.currentTimeMillis(); }
    protected void execute() {}
    protected boolean isFinished() { return(System.currentTimeMillis()-startTime >= duration); }
    protected void end() {}
    protected void interrupted() { this.cancel(); }
}
