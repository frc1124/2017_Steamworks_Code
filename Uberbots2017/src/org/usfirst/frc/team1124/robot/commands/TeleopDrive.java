package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TeleopDrive extends Command {

	public TeleopDrive() {
		requires(Robot.drive);
	}

	protected void execute() {
		if (usingMech()) {
			double dir = ((Math.atan2(-OI.stick.getRawAxis(5), OI.stick.getRawAxis(4)) * 180 / Math.PI) + 360) % 360;
			double mag = Math.sqrt(Math.pow(OI.stick.getRawAxis(5), 2) + Math.pow(OI.stick.getRawAxis(4), 2));

			Robot.drive.mechDrive(dir, mag);
		} else {
			Robot.drive.getRobotDrive().arcadeDrive(OI.stick);
		}
	}

	private boolean usingMech() {
		return Math.abs(OI.stick.getRawAxis(5)) > 0.1 || Math.abs(OI.stick.getRawAxis(4)) > 0.1;
	}

	protected boolean isFinished() {
		// It's never over
		return (false);
	}

	protected void end() {}

	protected void interrupted() {}

	protected void initilize() {}

}
