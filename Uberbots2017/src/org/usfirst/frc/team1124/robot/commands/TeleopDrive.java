package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class TeleopDrive extends Command {
	private boolean resetPoint = false;

	public TeleopDrive() {
		requires(Robot.drive);
	}

	protected void initilize() {
		Robot.drive.setTurnPoint(Robot.drive.getNavx().getYaw());
	}

	protected void execute() {
		if(resetPoint) { 
			Robot.drive.setTurnPoint(Robot.drive.getNavx().getYaw());
			resetPoint = false;
		}
		if(Math.abs(OI.stick.getRawAxis(4)) >= 0.1f || Math.abs(OI.stick.getRawAxis(5)) >= 0.1f) {
			Drive.getFrontLeft().setInverted(true);
			Drive.getRearLeft().setInverted(true);
			if(Math.abs(OI.stick.getRawAxis(0)) >= 0.1f || Math.abs(OI.stick.getRawAxis(1)) >= 0.1f) { Robot.drive.setTurnPoint(Math.atan2(OI.stick.getRawAxis(0), OI.stick.getRawAxis(1))-90); }
			Robot.drive.getDrive().mecanumDrive_Cartesian(OI.stick.getRawAxis(4), OI.stick.getRawAxis(5), Robot.drive.getTurnController().getOutput(Robot.drive.getNavx().getYaw(), Robot.drive.getTurnPoint()), 0);
		}else {
			Drive.getFrontLeft().setInverted(false);
			Drive.getRearLeft().setInverted(false);
			this.arcadeDrive(-OI.stick.getRawAxis(1), OI.stick.getRawAxis(0), 0);
			resetPoint = true;
		}
//		NetworkTable.getTable("jsDashboard").putNumber("it go",7);
//		NetworkTable.getTable("jsDashboard").putNumber("frontLeft", Drive.getFrontLeft().getOutputVoltage());
//		NetworkTable.getTable("jsDashboard").putNumber("rearLeft", Drive.getRearLeft().getOutputVoltage());
//		NetworkTable.getTable("jsDashboard").putNumber("frontRight", Drive.getFrontRight().getOutputVoltage());
//		NetworkTable.getTable("jsDashboard").putNumber("rearRight", Drive.getRearRight().getOutputVoltage());
	}

	public void arcadeDrive(double throttle, double turn, double correction){
		double leftSpeed = clamp(-turn - throttle - correction, -1, 1);
		double rightSpeed = clamp(-turn + throttle - correction, -1, 1);
		
		Drive.getFrontLeft().set(leftSpeed);
		Drive.getRearLeft().set(leftSpeed);
		Drive.getFrontRight().set(rightSpeed);
		Drive.getRearRight().set(rightSpeed);
	}
	private double clamp(double x, double low, double high){ return Math.max(Math.min(x, high), low); }
	
	protected boolean isFinished() { return (false); }
	protected void end() {}
	protected void interrupted() {}
}
