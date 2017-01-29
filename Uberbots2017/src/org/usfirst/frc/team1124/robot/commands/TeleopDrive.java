package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.Robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Command;
import utils.TableManager;

public class TeleopDrive extends Command {

	private static final double PID_BUFFER = 0.9;

	private static final boolean MEC = true;
	private static final boolean ARCADE = false;

	private boolean mode = ARCADE;
	private double lastAngleOfTrans = 0;

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
			Robot.drive.setLockAngle(0);
			lastAngleOfTrans = Math.toDegrees(Math.atan2(-OI.stick.getRawAxis(5), OI.stick.getRawAxis(4)));
		}
		mode = MEC;

		AHRS navx = Robot.drive.getNavx();

		double yaw = Robot.drive.getNavx().getYaw();
		double yaw2 = yaw + 360;
		double yaw3 = yaw - 360;

		yaw2 = (Math.abs(Robot.drive.getLockAngle() - yaw2) < Math.abs(Robot.drive.getLockAngle() - yaw3)) ? yaw2 : yaw3;
		yaw = (Math.abs(Robot.drive.getLockAngle() - yaw) < Math.abs(Robot.drive.getLockAngle() - yaw2)) ? yaw : yaw2;

		double rotation = Robot.drive.getTurnController().getOutput(yaw, Robot.drive.getLockAngle());

		double nextTransAngle = Math.toDegrees(Math.atan2(-OI.stick.getRawAxis(5), OI.stick.getRawAxis(4)));
		if (nextTransAngle < 0)
			nextTransAngle += 360;
		Robot.drive.setTransAngle(nextTransAngle);

		double actualAngle = Math.toDegrees(Math.atan2(navx.getVelocityX(), navx.getVelocityY()));
		if (actualAngle < 0)
			actualAngle += 360;
		double actualAngle2 = actualAngle + 360;
		double actualAngle3 = actualAngle - 360;

		actualAngle2 = (Math.abs(Robot.drive.getTransAngle() - actualAngle2) < Math.abs(Robot.drive.getTransAngle() - actualAngle3)) ? actualAngle2 : actualAngle3;
		actualAngle = (Math.abs(Robot.drive.getTransAngle() - actualAngle) < Math.abs(Robot.drive.getTransAngle() - actualAngle2)) ? actualAngle : actualAngle2;

		double corr = Robot.drive.getTransAngleController().getOutput(actualAngle, Robot.drive.getTransAngle());

		double angleOfTrans = lastAngleOfTrans;

		angleOfTrans += corr;
		angleOfTrans %= 360;
		angleOfTrans += 360;
		angleOfTrans %= 360;

		double mag = Math.hypot(OI.stick.getRawAxis(5), OI.stick.getRawAxis(4)) * PID_BUFFER;

		Robot.drive.getDrive().mecanumDrive_Cartesian(mag * Math.cos(Math.toRadians(angleOfTrans)), -mag * Math.sin(Math.toRadians(angleOfTrans)), rotation, 0);
		TableManager.put("dataTable", "actualAngle", actualAngle);
		TableManager.put("dataTable", "angle", Robot.drive.getTransAngle());
		TableManager.put("dataTable", "tryAngle", angleOfTrans);
		lastAngleOfTrans = angleOfTrans;
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
