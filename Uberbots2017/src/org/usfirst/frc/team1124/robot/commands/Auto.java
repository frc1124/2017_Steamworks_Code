package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class Auto extends Command {

    public Auto() {
        requires(Robot.drive);
    }
    protected void initialize() {
    	Robot.drive.rearRight.setEncPosition(0);
    }

    protected void execute() {
    	Robot.drive.frontLeft.set(-0.4);
    	Robot.drive.frontRight.set(0.4);
    	Robot.drive.rearLeft.set(0.4);
    	Robot.drive.rearRight.set(-0.4);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
