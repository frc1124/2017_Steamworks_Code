package org.usfirst.frc.team1124.robot.auto;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.utils.VisionComms;
import edu.wpi.first.wpilibj.command.Command;

public class TargetComponent extends Command {
	private double threshhold = 0.075;

    public TargetComponent() { this.requires(Robot.chassis); }

    protected void initialize() {}
    protected void execute() { Robot.chassis.strafe(VisionComms.read()); }
    protected boolean isFinished() { return(Math.abs(VisionComms.read()) < threshhold); }
    protected void end() {}
    protected void interrupted() {}
}
