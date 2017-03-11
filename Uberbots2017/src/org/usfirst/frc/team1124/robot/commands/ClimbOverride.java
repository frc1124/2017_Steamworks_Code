package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimbOverride extends Command {

    public ClimbOverride() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }
    protected void initialize() {Robot.modeFlag = 11;}
    protected void execute() { Robot.climber.motorUpOverride(); }
    protected boolean isFinished() { return false; }
    protected void end() { Robot.climber.motorStop(); Robot.modeFlag = 0;}
    protected void interrupted() { this.end(); }
}
