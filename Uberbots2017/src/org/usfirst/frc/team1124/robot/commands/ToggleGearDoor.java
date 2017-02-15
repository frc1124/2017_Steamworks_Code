package org.usfirst.frc.team1124.robot.commands;
import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class ToggleGearDoor extends Command {

    public ToggleGearDoor() {}
    protected void initialize() { Robot.gearDoor.toggle(); }
    protected void execute() {}
    protected boolean isFinished() { return(true); }
    protected void end() {}
    protected void interrupted() {}
}
