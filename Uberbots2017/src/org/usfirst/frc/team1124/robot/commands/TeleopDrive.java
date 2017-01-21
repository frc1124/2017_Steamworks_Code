package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TeleopDrive extends Command {
	
	double angle = 0;
	
	public TeleopDrive() {
		requires(Robot.drive);
	}

	protected void execute() {
		OI.LeftX = OI.stick.getRawAxis(0);
		OI.LeftY = -OI.stick.getRawAxis(1);
		
		OI.RightX = OI.stick.getRawAxis(4);
		OI.RightY = -OI.stick.getRawAxis(5);
		
		Robot.drive.putDataOnTable();
		if (usingMech()) {
			double dir = ((Math.atan2(OI.RightY, OI.RightX) * 180 / Math.PI) + 360) % 360;
			double mag = Math.sqrt(Math.pow(OI.RightY, 2) + Math.pow(OI.RightX, 2));

			Robot.drive.mechDrive(dir, mag);
		} else {
			Robot.drive.getRobotDrive().arcadeDrive(OI.stick);
		}
	}

	private boolean usingMech() {
		return Math.abs(OI.RightX) > 0.1 || Math.abs(OI.RightY) > 0.1;
	}

	protected boolean isFinished() {
		// It's never over
		return (false);
	}

	protected void end() {}

	protected void interrupted() {}

	protected void initilize() {}

}
