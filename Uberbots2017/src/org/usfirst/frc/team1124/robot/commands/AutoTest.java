package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class AutoTest extends Command {
	private double targetX = 0.0;
	private double targetY = 0.0;
	private double targetAngle = 0.0;
	private boolean done = false;

	private double distanceTolerance = 0.01;
	private double angleTolerance = 0.5;

	public AutoTest(double dX, double dY, double angle) {
		this.targetX = dX;
		this.targetY = dY;
		this.targetAngle = angle;
		requires(Robot.drive);
	}

	protected void execute() {
		// Find the difference between where we sta
		double dx = this.targetX - Robot.drive.getX();
		double dy = this.targetY - Robot.drive.getY();
		double da = this.targetAngle - Robot.drive.getAngle();
		double adx = Math.abs(dx);
		double ady = Math.abs(dy);
		double ada = Math.abs(da);
		boolean xWithinTolerance = (adx < this.distanceTolerance);
		boolean yWithinTolerance = (ady < this.distanceTolerance);
		boolean aWithinTolerance = (ada < this.angleTolerance);
		if (this.done ||
			(xWithinTolerance &&
			yWithinTolerance &&
			aWithinTolerance)) {
			this.done = true;
			return;
		}

		// Figure out the velocities based on where we are and where we want to be
		double vX = (xWithinTolerance) ? 0.0 : 0.1;
		if (dx < 0.0) {
			vX *= -1;
		}
		double vY = (yWithinTolerance) ? 0.0 : 0.1;
		if (dy < 0.0) {
			vY *= -1;
		}
		double vRot = (aWithinTolerance) ? 0.0 : 0.10;
		if (ada < 10.0) {
			vRot = 0.01;
		}
		if (da < 0.0) {
			vRot *= -1;
		}

		// Drive towards target
		Robot.drive.doMecanum(vX,vY,vRot);
	}

	@Override
	protected boolean isFinished() {
		return this.done;
	}

}