package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CrossLine extends Command {
	
	double rotations;
	double val;
	double spd;

    public CrossLine(double val, double spd) {
        requires(Robot.drive);
        this.val = val;
        this.spd = spd;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	rotations = val/12.5;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	while(Robot.drive.wheels[1].getEncPosition() < rotations) {
    		Robot.drive.setSpeed(spd, 3);
    		Robot.drive.setSpeed(spd, 4);
    		Robot.drive.setSpeed(-spd, 1);
    		Robot.drive.setSpeed(-spd, 1);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
