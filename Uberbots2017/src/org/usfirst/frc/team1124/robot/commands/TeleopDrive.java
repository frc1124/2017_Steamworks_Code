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
		if (resetPoint) {
			Robot.drive.setTurnPoint(Robot.drive.getNavx().getYaw());
			resetPoint = false;
		}
		if (Math.abs(OI.stick.getRawAxis(4)) >= 0.1f || Math.abs(OI.stick.getRawAxis(5)) >= 0.1f) {
			Drive.getFrontLeft().setInverted(true);
			Drive.getRearLeft().setInverted(true);
			Robot.drive.getDrive().mecanumDrive_Cartesian(OI.stick.getRawAxis(4), OI.stick.getRawAxis(5),
					Robot.drive.getTurn().getOutput(Robot.drive.getNavx().getYaw(), Robot.drive.getTurnPoint()),
					Robot.drive.getNavx().getYaw());
			NetworkTable.getTable("correction").putNumber("thing",
					Robot.drive.getTurn().getOutput(Robot.drive.getNavx().getYaw()));
		} else {
			Drive.getFrontLeft().setInverted(false);
			Drive.getRearLeft().setInverted(false);
			this.arcadeDrive(-OI.stick.getRawAxis(1), OI.stick.getRawAxis(0), 0);
			resetPoint = true;
		}
		NetworkTable.getTable("CommandTest").putBoolean("it go", true);
	}

	public void arcadeDrive(double throttle, double turn, double correction) {
		double leftSpeed = -turn - throttle + correction;
		double rightSpeed = -turn + throttle + correction;
		if (Math.abs(Math.max(leftSpeed, rightSpeed)) > 1) {
			leftSpeed /= Math.abs(Math.max(leftSpeed, rightSpeed));
			rightSpeed /= Math.abs(Math.max(leftSpeed, rightSpeed));
		}

		Drive.getFrontLeft().set(leftSpeed);
		Drive.getRearLeft().set(leftSpeed);
		Drive.getFrontRight().set(rightSpeed);
		Drive.getRearRight().set(rightSpeed);
	}

	protected boolean isFinished() {
		return (false);
	}

	protected void end() {}

	protected void interrupted() {}
}
