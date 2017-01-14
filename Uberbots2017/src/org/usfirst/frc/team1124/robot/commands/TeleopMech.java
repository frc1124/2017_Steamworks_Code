package org.usfirst.frc.team1124.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.Robot;

public class TeleopMech extends Command {

	public TeleopMech() {
		requires(Robot.drive);
	}

	protected void execute() {
		double dir = ((Math.atan2(-OI.stick.getY(), OI.stick.getX()) * 180 / Math.PI) + 360) % 360;
		double mag = OI.stick.getMagnitude();

		Robot.drive.mechDrive(dir, mag);

	}

	protected boolean isFinished() {
		// It's never over
		return (false);
	}

	protected void end() {}

	protected void interrupted() {}

	protected void initilize() {}
}
