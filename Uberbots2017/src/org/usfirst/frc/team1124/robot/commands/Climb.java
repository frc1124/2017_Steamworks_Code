package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Climb extends Command {

    public Climb() {}
    protected void initialize() {}
    protected void execute() { Robot.climber.motorUp(); }
    protected boolean isFinished() { return false; }
    protected void end() { Robot.climber.motorStop(); }
    protected void interrupted() { this.end(); }
}
