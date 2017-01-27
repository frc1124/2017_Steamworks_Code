package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class TeleopDrive extends Command {
	private boolean resetPoint = false;

	public TeleopDrive() { requires(Robot.drive); }

	protected void initilize() {
		Robot.drive.setTurnPoint(Robot.drive.getNavx().getYaw());
	}

	protected void execute() {
		if (resetPoint) {
			Robot.drive.setTurnPoint(Robot.drive.getNavx().getYaw());
			resetPoint = false;
		}
		if (Math.abs(OI.stick.getRawAxis(4)) >= 0.1f || Math.abs(OI.stick.getRawAxis(5)) >= 0.1f) {
			Robot.drive.getFrontLeft().setInverted(true);
			Robot.drive.getRearLeft().setInverted(true);
			if (Math.abs(OI.stick.getRawAxis(0)) >= 0.1f || Math.abs(OI.stick.getRawAxis(1)) >= 0.1f) {
				Robot.drive.setTurnPoint(Math.atan2(OI.stick.getRawAxis(0), OI.stick.getRawAxis(1)) - 90);
			}
			Robot.drive.getDrive()
					.mecanumDrive_Cartesian(OI.stick.getRawAxis(4), OI.stick.getRawAxis(5), Robot.drive
							.getTurnController().getOutput(Robot.drive.getNavx().getYaw(), Robot.drive.getTurnPoint()),
							0);
		} else {
			Robot.drive.getFrontLeft().setInverted(false);
			Robot.drive.getRearLeft().setInverted(false);
			this.arcadeDrive(-OI.stick.getRawAxis(1), OI.stick.getRawAxis(0), 0);
			resetPoint = true;
		}
		NetworkTable.getTable("jsDashboard").putNumber("it go", 7);
		NetworkTable.getTable("jsDashboard").putNumber("frontLeft", Robot.drive.getFrontLeft().getOutputVoltage());
		NetworkTable.getTable("jsDashboard").putNumber("rearLeft", Robot.drive.getRearLeft().getOutputVoltage());
		NetworkTable.getTable("jsDashboard").putNumber("frontRight", Robot.drive.getFrontRight().getOutputVoltage());
		NetworkTable.getTable("jsDashboard").putNumber("rearRight", Robot.drive.getRearRight().getOutputVoltage());
	}

	public void arcadeDrive(double throttle, double turn, double correction) {
		double leftSpeed = -turn - throttle + correction;
		double rightSpeed = -turn + throttle + correction;
		if (Math.abs(Math.max(leftSpeed, rightSpeed)) > 1) {
			leftSpeed /= Math.abs(Math.max(leftSpeed, rightSpeed));
			rightSpeed /= Math.abs(Math.max(leftSpeed, rightSpeed));
		}

		Robot.drive.getFrontLeft().set(leftSpeed);
		Robot.drive.getRearLeft().set(leftSpeed);
		Robot.drive.getFrontRight().set(rightSpeed);
		Robot.drive.getRearRight().set(rightSpeed);
	}

	protected boolean isFinished() {
		return (false);
	}

	protected void end() {}

	protected void interrupted() {}
}
