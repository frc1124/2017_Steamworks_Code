package org.usfirst.frc.team1124.robot.commands;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team1124.robot.OI;

public class TeleopArcade extends Command {
	private PWM wheelOne;
	private PWM wheelTwo;
	private PWM wheelThree;
	private PWM wheelFour;

	private double x;
	private double y;

	public TeleopArcade() {
		wheelOne = new PWM(0);
		wheelTwo = new PWM(1);
		wheelThree = new PWM(2);
		wheelFour = new PWM(3);
	}

	protected void execute() {
		x = OI.stick.getX();
		y = OI.stick.getY();
		
		wheelOne.setSpeed(y*x);
		wheelOne.setSpeed(y*x);
		wheelThree.setSpeed(y*x);
		wheelFour.setSpeed(y*x);
	}

	protected boolean isFinished() { return (false); }
	protected void end() {}
	protected void interrupted() {}
	protected void initilize() {}
}
