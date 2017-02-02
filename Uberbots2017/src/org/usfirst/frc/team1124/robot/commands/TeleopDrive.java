package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.Robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Command;
import utils.MiniPID;
import utils.TableManager;

public class TeleopDrive extends Command {

	// Buffer for PID error correction
	private static final double PID_BUFFER = 0.5;

	// To know what mode it is in
	private Mode mode = Mode.ARCADE;

	// Which direction did the robot try last time?
	private double lastTryAngle = 0;

	public TeleopDrive() {
		requires(Robot.drive);
	}

	protected void initilize() {
		Robot.drive.setWantedYaw(Robot.drive.getNavx().getYaw());
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
		mode = Mode.ARCADE;

		double stickX = OI.stick.getRawAxis(1);
		double stickY = -OI.stick.getRawAxis(0);

		this.arcadeDrive(-stickX, stickY);
	}

	private void executeMech() {
		// Things needed to be done when just getting into mecanum
		if (mode == Mode.ARCADE) {
			Robot.drive.setWantedYaw(Robot.drive.getNavx().getYaw());
			lastTryAngle = Math.toDegrees(Math.atan2(-OI.stick.getRawAxis(5), OI.stick.getRawAxis(4)));
		}
		mode = Mode.MEC;

		AHRS navx = Robot.drive.getNavx();

		updateWantedDirection();

		// Finding out how much the robot needs to rotate to be correct
		double yaw = Robot.drive.getNavx().getYaw();
		double rotation = getFastAngularCorrection(yaw, Robot.drive.getWantedYaw(), Robot.drive.getYawController());

		// Finding out which the direction the robot should be trying to move in order to achieve the correct direction

		double actualDirection = Math.toDegrees(Math.atan2(navx.getVelocityX(), navx.getVelocityY())) + navx.getYaw();
		double tryAngleIncrease = 0;// getFastAngularCorrection(actualDirection, Robot.drive.getWantedDirection(), Robot.drive.getDirectionController());
		double tryAngle = lastTryAngle + tryAngleIncrease;
		tryAngle %= 360;
		lastTryAngle = tryAngle;

		// moving
		double mag = Math.hypot(OI.stick.getRawAxis(5), OI.stick.getRawAxis(4)) * PID_BUFFER;
		double rightMovement = mag * Math.cos(Math.toRadians(tryAngle));
		double forwardMovement = -mag * Math.sin(Math.toRadians(tryAngle));

		Robot.drive.getFrontLeft().set(0.5);
		Robot.drive.getFrontRight().set(0.5);
		Robot.drive.getRearLeft().set(0.5);
		Robot.drive.getRearRight().set(0.5);
		// Robot.drive.getDrive().mecanumDrive_Cartesian(OI.stick.getRawAxis(4), OI.stick.getRawAxis(5), rotation, 0);

		// debugging
		TableManager.put("dataTable", "frontLeft", Robot.drive.getFrontLeft().getOutputVoltage());
		TableManager.put("dataTable", "frontRight", Robot.drive.getFrontRight().getOutputVoltage());
		TableManager.put("dataTable", "rearLeft", Robot.drive.getRearLeft().getOutputVoltage());
		TableManager.put("dataTable", "rearRight", Robot.drive.getRearRight().getOutputVoltage());
	}

	private void updateWantedDirection() {
		double wantedDirection = Math.toDegrees(Math.atan2(-OI.stick.getRawAxis(5), OI.stick.getRawAxis(4)));
		if (wantedDirection < 0)
			wantedDirection += 360;
		Robot.drive.setWantedDirection(wantedDirection);
	}

	private double getFastAngularCorrection(double angle, double setAngle, MiniPID pid) {
		// Finding out which of, angle, angle + 360, and angle - 360, is closest to the set angle
		double angle2 = angle + 360;
		double angle3 = angle - 360;

		double angle4 = (Math.abs(setAngle - angle2) < Math.abs(setAngle - angle3)) ? angle2 : angle3;
		double angle5 = (Math.abs(setAngle - angle) < Math.abs(setAngle - angle4)) ? angle : angle4;

		return pid.getOutput(angle5, setAngle);
	}

	public void arcadeDrive(double throttle, double turn) {
		// Basically a copy of the built in arcadeDrive

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

	private static enum Mode {
		ARCADE, MEC;
	}
}
