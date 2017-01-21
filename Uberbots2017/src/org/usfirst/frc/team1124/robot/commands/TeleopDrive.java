package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;

public class TeleopDrive extends Command implements PIDOutput {
	public static PIDController turnController;
	double angle = 0;
	double correction = 0;
	double dir;

	private static final double P = 0.005f;
	private static final double I = 0.0f;
	private static final double D = 0.0f;

	public TeleopDrive() {
		requires(Robot.drive);
		turnController = new PIDController(P, I, D, 0.0f, Robot.drive.navX, this);
		turnController.setInputRange(-180.0f, 180.0f);
		turnController.setOutputRange(-1.0, 1.0);
		turnController.setAbsoluteTolerance(2);
		turnController.setContinuous(true);
	}

	protected void execute() {
		OI.LeftX = OI.stick.getRawAxis(0);
		OI.LeftY = -OI.stick.getRawAxis(1);

		OI.RightX = OI.stick.getRawAxis(4);
		OI.RightY = -OI.stick.getRawAxis(5);

		double mag = Math.sqrt(Math.pow(OI.RightY, 2) + Math.pow(OI.RightX, 2));

		Robot.drive.putDataOnTable();
		if (usingMech()) {
			double dir = ((Math.atan2(OI.RightY, OI.RightX) * 180 / Math.PI) + 360) % 360;
			turnController.setSetpoint(Math.atan2(OI.RightX, OI.RightY));
			Drive.table.putNumber("Magnitude", mag);
			Drive.table.putNumber("Direction", dir);

			Robot.drive.mechDrive(dir + correction, mag);
		} else {
			Robot.drive.getRobotDrive().arcadeDrive(mag, OI.stick.getDirectionDegrees() + correction);
		}
	}

	private boolean usingMech() {
		return Math.abs(OI.RightX) > 0.1 || Math.abs(OI.RightY) > 0.1;
	}

	protected boolean isFinished() {
		// It's never over
		return (false);
	}

	protected void end() {}

	protected void interrupted() {}

	protected void initilize() {}

	@Override
	public void pidWrite(double output) {
		this.correction = -output;
	}

}
