package org.usfirst.frc.team1124.robot.commands;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.RobotMap;

public class TeleopMech extends Command {

	private double dir;
	private double mag;

	public TeleopMech() { requires(Robot.drive); }
	
	protected void execute() {
		dir = ((Math.atan2(-OI.stick.getY(), OI.stick.getX()) * 180 / Math.PI) + 360)%360;
		mag = OI.stick.getMagnitude();
		
		if(dir >=0 && dir < 90) {
			double a = Math.sin(Math.toRadians(dir-45));
			double b = Math.cos(Math.toRadians(dir-45));
			a*=mag;
			b*=mag;
			Robot.drive.setSpeedOne(b);
			Robot.drive.setSpeedTwo(a);
			Robot.drive.setSpeedThree(b);
			Robot.drive.setSpeedFour(a);
		}
		if(dir >= 90 && dir < 180) {
			double b = Math.sin(Math.toRadians(135-dir));
			double a = Math.cos(Math.toRadians(135-dir));
			a*=mag;
			b*=mag;
			Robot.drive.setSpeedOne(b);
			Robot.drive.setSpeedTwo(a);
			Robot.drive.setSpeedThree(b);
			Robot.drive.setSpeedFour(a);
		}
		if(dir >= 180 && dir < 270) {
			double a = -Math.sin(Math.toRadians(dir-180-45));
			double b = -Math.cos(Math.toRadians(dir-180-45));
			a*=mag;
			b*=mag;
			Robot.drive.setSpeedOne(b);
			Robot.drive.setSpeedTwo(a);
			Robot.drive.setSpeedThree(b);
			Robot.drive.setSpeedFour(a);
		}
		if(dir >= 270 && dir < 360) {
			double b = -Math.sin(Math.toRadians(135-dir+180));
			double a = -Math.cos(Math.toRadians(135-dir+180));
			a*=mag;
			b*=mag;
			Robot.drive.setSpeedOne(b);
			Robot.drive.setSpeedTwo(a);
			Robot.drive.setSpeedThree(b);
			Robot.drive.setSpeedFour(a);
		}
	}

	protected boolean isFinished() {return (false);}

	protected void end() {}

	protected void interrupted() {}

	protected void initilize() {}
}
