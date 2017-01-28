package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import utils.TableManager;

public class TeleopDrive extends Command {

	private static final boolean MEC = true;
	private static final boolean ARCADE = false;

	private boolean mode = ARCADE;

	public TeleopDrive() {
		requires(Robot.drive);
	}

	protected void initilize() {
		Robot.drive.setLockAngle(Robot.drive.getNavx().getYaw());
	}

	protected void execute() {
		if (Math.abs(OI.stick.getRawAxis(4)) >= 0.1f || Math.abs(OI.stick.getRawAxis(5)) >= 0.1f) {
			executeMech();
		} else {
			executeArcade();
		}
		debug();
	}

	private void debug() {
		TableManager.put("jsDashboard", "frontLeft", Robot.drive.getFrontLeft().getOutputVoltage());
		TableManager.put("jsDashboard", "rearLeft", Robot.drive.getRearLeft().getOutputVoltage());
		TableManager.put("jsDashboard", "frontRight", Robot.drive.getFrontRight().getOutputVoltage());
		TableManager.put("jsDashboard", "rearRight", Robot.drive.getRearRight().getOutputVoltage());
	}

	private void executeArcade() {
		mode = ARCADE;

		Robot.drive.getFrontLeft().setInverted(false);
		Robot.drive.getRearLeft().setInverted(false);

		this.arcadeDrive(-OI.stick.getRawAxis(1), -OI.stick.getRawAxis(0), 0);
	}

	private void executeMech() {
		if (mode == ARCADE)
			Robot.drive.setLockAngle(Robot.drive.getNavx().getYaw());
		mode = MEC;

		Robot.drive.getFrontLeft().setInverted(true);
		Robot.drive.getRearLeft().setInverted(true);

		if (Math.abs(OI.stick.getRawAxis(0)) >= 0.1f || Math.abs(OI.stick.getRawAxis(1)) >= 0.1f) {
			Robot.drive.setLockAngle(Math.atan2(OI.stick.getRawAxis(0), OI.stick.getRawAxis(1)) - 90);
		}

		double rotation = Robot.drive.getTurnController().getOutput(Robot.drive.getNavx().getYaw(), Robot.drive.getLockAngle());
		Robot.drive.getDrive().mecanumDrive_Cartesian(OI.stick.getRawAxis(4), OI.stick.getRawAxis(5), rotation, 0);
	}

	public void arcadeDrive(double throttle, double turn, double correction) {
		turn *= throttle / Math.abs(throttle);

		double leftSpeed = turn - throttle + correction;
		double rightSpeed = turn + throttle + correction;

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
