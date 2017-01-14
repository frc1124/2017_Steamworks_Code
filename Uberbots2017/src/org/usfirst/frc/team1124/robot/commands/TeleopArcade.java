package org.usfirst.frc.team1124.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.Robot;

public class TeleopArcade extends Command {
	
	public TeleopArcade() { requires(Robot.drive); }

	protected void execute() {Robot.drive.getRobotDrive().arcadeDrive(OI.stick);}

	protected boolean isFinished() {return (false);}

	protected void end() {}
	
	protected void interrupted() {}
	
	protected void initilize() {}
}
