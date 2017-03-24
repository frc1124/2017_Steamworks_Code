package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class ToggleGearDoor extends Command {

    public ToggleGearDoor() { this.requires(Robot.gearDoor); }

    protected void initialize() {}
    protected void execute() { Robot.gearDoor.toggle(); }
    protected boolean isFinished() { return(true); }
    protected void end() {}
    protected void interrupted() { }
}
