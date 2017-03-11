package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import utils.MiniPID;

/**
 *
 */
public class JsVision extends Command {
	private static MiniPID jsPID = new MiniPID(0.01,0,0.01);
	private boolean done = false;
	
    public JsVision() { 
    	requires(Robot.drive);
    	jsPID.setOutputLimits(0.4);
    	jsPID.setSetpoint(0);
    }
    protected void initialize() {
    	Robot.drive.mode = 2;
    	NetworkTable.getTable("jsVision").putNumber("offset", 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Math.abs(NetworkTable.getTable("jsVision").getNumber("offset")) > 30) { Robot.drive.run(jsPID.getOutput(NetworkTable.getTable("jsVision").getNumber("offset")), 0); }
    	else { this.done = true; }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.cancel();
    }
}
