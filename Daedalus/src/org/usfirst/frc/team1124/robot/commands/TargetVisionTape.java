package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.utils.VisionComms;
import edu.wpi.first.wpilibj.command.Command;

public class TargetVisionTape extends Command {
	private double threshhold = 0.05;

    public TargetVisionTape() { this.requires(Robot.chassis); }

    protected void initialize() {}
    protected void execute() { Robot.chassis.strafe(VisionComms.read()); }
    protected boolean isFinished() { return(false); }
    protected void end() {}
    protected void interrupted() { this.cancel(); }
}
