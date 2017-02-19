package org.usfirst.frc.team1124.robot.commands;
import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class ReverseClimb extends Command {

    public ReverseClimb() {}
    protected void initialize() {}
    protected void execute() { Robot.climber.motorDown(); }
    protected boolean isFinished() { return false; }
    protected void end() { Robot.climber.motorStop(); }
    protected void interrupted() { this.end(); }
}
