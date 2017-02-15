package org.usfirst.frc.team1124.robot.commands;
import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class ToggleClimbDoor extends Command {

    public ToggleClimbDoor() {}
    protected void initialize() { Robot.climber.toggle(); }
    protected void execute() {}
    protected boolean isFinished() { return(true); }
    protected void end() {}
    protected void interrupted() {
    }
}
