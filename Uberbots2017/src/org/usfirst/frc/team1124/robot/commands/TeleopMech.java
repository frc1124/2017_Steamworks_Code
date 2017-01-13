package org.usfirst.frc.team1124.robot.commands;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.RobotMap;

public class TeleopMech extends Command {
	private PWM wheelOne;
	private PWM wheelTwo;
	private PWM wheelThree;
	private PWM wheelFour;

	private double dir;
	private double mag;

	public TeleopMech() {
		wheelOne = new PWM(RobotMap.FRONT_LEFT);
		wheelTwo = new PWM(RobotMap.FRONT_RIGHT);
		wheelThree = new PWM(RobotMap.BACK_LEFT);
		wheelFour = new PWM(RobotMap.BACK_RIGHT);
	}

	protected void execute() {
		dir = OI.stick.getDirectionRadians();
		mag = OI.stick.getMagnitude();

		// lateral motion
		if ((dir >= 0) && (dir <= 1 * Math.PI / 2)) {
			wheelOne.setSpeed(mag);
			wheelTwo.setSpeed((dir * (4 / Math.PI) - 1) * mag);
			wheelThree.setSpeed((dir * (4 / Math.PI) - 1) * mag);
			wheelFour.setSpeed(mag);
		}
		if ((dir >= Math.PI / 2) && (dir <= Math.PI)) {
			wheelOne.setSpeed((dir * (-4 / Math.PI) + 3) * mag);
			wheelTwo.setSpeed(mag);
			wheelThree.setSpeed(mag);
			wheelFour.setSpeed((dir * (-4 / Math.PI) + 3) * mag);
		}
		if ((dir >= Math.PI) && (dir <= 3 * Math.PI / 2)) {
			wheelOne.setSpeed(mag);
			wheelTwo.setSpeed((dir * (-4 / Math.PI) + 5) * mag);
			wheelThree.setSpeed((dir * (-4 / Math.PI) + 5) * mag);
			wheelFour.setSpeed(mag);
		}
		if ((dir >= 3 * Math.PI / 2)) {
			wheelOne.setSpeed((dir * (4 / Math.PI) + 7) * mag);
			wheelTwo.setSpeed(mag);
			wheelThree.setSpeed(mag);
			wheelFour.setSpeed((dir * (4 / Math.PI) + 7) * mag);
		}
	}

	protected boolean isFinished() {return (false);}

	protected void end() {}

	protected void interrupted() {}

	protected void initilize() {}
}
