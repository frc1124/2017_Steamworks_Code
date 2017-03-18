package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class ToggleRopeDoor extends Command {

    public ToggleRopeDoor() { this.requires(Robot.climber); }

    protected void initialize() {}
    protected void execute() { Robot.climber.toggle(); }
    protected boolean isFinished() { return(true); }
    protected void end() {}
    protected void interrupted() { this.cancel(); }
}
