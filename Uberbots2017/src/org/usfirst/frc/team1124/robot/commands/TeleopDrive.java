package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.Robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import utils.TableManager;

public class TeleopDrive extends Command {

	private static final double PID_BUFFER = 0.9;

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

	private void debug() {}

	private void executeArcade() {
		mode = ARCADE;

		this.arcadeDrive(-OI.stick.getRawAxis(1), -OI.stick.getRawAxis(0), 0);
	}

	private void executeMech() {
		if (mode == ARCADE) {
			Robot.drive.getNavx().reset();
			Robot.drive.getNavx().resetDisplacement();
			Robot.drive.setLockAngle(Robot.drive.getNavx().getYaw());
		}
		mode = MEC;

		AHRS navx = Robot.drive.getNavx();

		double rotation = Robot.drive.getTurnController().getOutput(Robot.drive.getNavx().getYaw(), Robot.drive.getLockAngle());

		double corr = Robot.drive.getTransAngleController().getOutput(Math.toDegrees(Math.atan2(navx.getVelocityX(), navx.getVelocityY())), Robot.drive.getTransAngle());
		double corr2 = corr + 360;
		double corr3 = corr - 360;

		corr2 = (Math.abs(corr2) < Math.abs(corr3)) ? corr2 : corr3;
		corr = (Math.abs(corr) < Math.abs(corr2)) ? corr : corr2;

		double angleOfTrans = Math.toDegrees(-Math.atan2(OI.stick.getRawAxis(5), OI.stick.getRawAxis(4)));

		angleOfTrans += corr;
		angleOfTrans %= 360;
		angleOfTrans += 360;
		angleOfTrans %= 360;

		double mag = Math.hypot(OI.stick.getRawAxis(5), OI.stick.getRawAxis(4)) * PID_BUFFER;

		Robot.drive.getDrive().mecanumDrive_Cartesian(mag * Math.cos(Math.toRadians(angleOfTrans)), -mag * Math.sin(Math.toRadians(angleOfTrans)), rotation, 0);
		TableManager.put("jsDashboard","graph1", rotation);
	}

	public void arcadeDrive(double throttle, double turn, double correction) {
		double leftMotorSpeed;
		double rightMotorSpeed;

		throttle = throttle * throttle * Math.signum(throttle);
		turn = turn * turn * Math.signum(turn);

		if (throttle > 0.0) {
			if (turn > 0.0) {
				leftMotorSpeed = throttle - turn;
				rightMotorSpeed = Math.max(throttle, turn);
			} else {
				leftMotorSpeed = Math.max(throttle, -turn);
				rightMotorSpeed = throttle + turn;
			}
		} else {
			if (turn > 0.0) {
				leftMotorSpeed = -Math.max(-throttle, turn);
				rightMotorSpeed = throttle + turn;
			} else {
				leftMotorSpeed = throttle - turn;
				rightMotorSpeed = -Math.max(-throttle, -turn);
			}
		}

		if (Math.max(Math.abs(leftMotorSpeed), Math.abs(rightMotorSpeed)) > 1) {
			double d = Math.max(Math.abs(leftMotorSpeed), Math.abs(rightMotorSpeed));
			leftMotorSpeed *= PID_BUFFER / d;
			rightMotorSpeed *= PID_BUFFER / d;
		}

		Robot.drive.getFrontLeft().set(leftMotorSpeed);
		Robot.drive.getRearLeft().set(leftMotorSpeed);
		Robot.drive.getFrontRight().set(rightMotorSpeed);
		Robot.drive.getRearRight().set(rightMotorSpeed);
	}

	protected boolean isFinished() {
		return (false);
	}

	protected void end() {}

	protected void interrupted() {}
}
