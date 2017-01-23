package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class TeleopDrive extends Command {

	public TeleopDrive() { requires(Robot.drive); }
																															 /*               \/This bit is probably wrong\/                 */
	protected void execute() { Robot.drive.getDrive().mecanumDrive_Cartesian(OI.stick.getRawAxis(0), OI.stick.getRawAxis(1), Robot.drive.getTurn().getOutput(Robot.drive.getNavx().getYaw(), 0), Robot.drive.getNavx().getYaw()); }
	protected boolean isFinished() { return(false); }
	protected void end() {}
	protected void interrupted() {}
	protected void initilize() {}
}
