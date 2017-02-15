package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Climb extends Command {

    public Climb() { requires(Robot.climber); }
    protected void initialize() {}
    protected void execute() { Robot.climber.getMotor().set(1); }
    protected boolean isFinished() { return false; }
    protected void end() { Robot.climber.getMotor().set(0.0); }
    protected void interrupted() { this.end(); }
}
