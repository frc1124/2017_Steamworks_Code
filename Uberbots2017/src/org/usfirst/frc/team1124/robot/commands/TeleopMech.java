package org.usfirst.frc.team1124.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.Robot;

public class TeleopMech extends Command {

	private double dir;
	private double mag;

	private static final double PID_BUFFER = 0.95;

	public TeleopMech() {
		requires(Robot.drive);
	}

	protected void execute() {
		dir = ((Math.atan2(-OI.stick.getY(), OI.stick.getX()) * 180 / Math.PI) + 360) % 360;
		mag = OI.stick.getMagnitude();

		double a = Math.sin(Math.toRadians(dir - 45));
		double b = Math.cos(Math.toRadians(dir - 45));
		a *= mag * PID_BUFFER;
		b *= mag * PID_BUFFER;
		Robot.drive.setSpeedOne(b);
		Robot.drive.setSpeedTwo(a);
		Robot.drive.setSpeedThree(a);
		Robot.drive.setSpeedFour(b);
	}

	protected boolean isFinished() {
		// It's never over
		return (false);
	}

	protected void end() {}

	protected void interrupted() {}

	protected void initilize() {}
}
