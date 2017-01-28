package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Autonomous extends Command {

	double lastDisplacement = 0;

	public Autonomous() {
		requires(Robot.drive);
	}

	public void execute() {
		AHRS navx = Robot.drive.getNavx();

		Robot.drive.getFrontLeft().set(-0.25);
		Robot.drive.getFrontRight().set(0.25);
		Robot.drive.getRearLeft().set(-0.25);
		Robot.drive.getRearRight().set(0.25);

		NetworkTable.getTable("jsDashboard").putNumber("frontLeft", Robot.drive.getFrontLeft().getOutputVoltage());
		NetworkTable.getTable("jsDashboard").putNumber("rearLeft", Robot.drive.getRearLeft().getOutputVoltage());
		NetworkTable.getTable("jsDashboard").putNumber("frontRight", Robot.drive.getFrontRight().getOutputVoltage());
		NetworkTable.getTable("jsDashboard").putNumber("rearRight", Robot.drive.getRearRight().getOutputVoltage());

		NetworkTable.getTable("dataTable").putNumber("dx", Robot.drive.getNavx().getDisplacementX());
		NetworkTable.getTable("dataTable").putNumber("dy", Robot.drive.getNavx().getDisplacementY());
		NetworkTable.getTable("dataTable").putNumber("dz", Robot.drive.getNavx().getDisplacementZ());

		double currDisplacement = Math.hypot(navx.getDisplacementX(), navx.getDisplacementY());

		NetworkTable.getTable("jsDashboard").putNumber("displacement", currDisplacement - lastDisplacement);
		lastDisplacement = currDisplacement;
	}

	protected boolean isFinished() {
		return false;
	}

}
