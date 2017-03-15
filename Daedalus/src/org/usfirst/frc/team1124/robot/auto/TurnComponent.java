package org.usfirst.frc.team1124.robot.auto;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.subsystems.Drive;
import edu.wpi.first.wpilibj.command.Command;

public class TurnComponent extends Command {
	private double angle;

    public TurnComponent(double angle) {
        this.requires(Robot.chassis);
        this.angle = angle;
    }
    protected void initialize() {}
    protected void execute() {}
    protected boolean isFinished() { return(Math.abs(Drive.navx.getYaw()-angle) < 10); }
    protected void end() {}
    protected void interrupted() {}
}
