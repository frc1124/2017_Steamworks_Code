package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class TeleopDrive extends Command {
	private boolean resetPoint = false;

	public TeleopDrive() { requires(Robot.drive); }
	
	protected void initilize() { Robot.drive.setTurnPoint(Robot.drive.getNavx().getYaw()); }
	protected void execute() {
		if(resetPoint) { Robot.drive.setTurnPoint(Robot.drive.getNavx().getYaw()); resetPoint = false; }
		if(OI.stick.getRawAxis(4) != 0 || OI.stick.getRawAxis(5) != 0) { Robot.drive.getDrive().mecanumDrive_Cartesian(OI.stick.getRawAxis(4), OI.stick.getRawAxis(5), Robot.drive.getTurn().getOutput(Robot.drive.getNavx().getYaw(), Robot.drive.getTurnPoint()), Robot.drive.getNavx().getYaw()); }
		else { Robot.drive.getDrive().arcadeDrive(OI.stick); resetPoint = true; }
	}
	protected boolean isFinished() { return(false); }
	protected void end() {}
	protected void interrupted() {}
}
