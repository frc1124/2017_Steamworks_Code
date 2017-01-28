package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Autonomous extends Command {

	public Autonomous() {
		requires(Robot.drive);
	}

	public void initialize() {
		AHRS navx = Robot.drive.getNavx();

		Robot.drive.getNavx().reset();
		Robot.drive.getNavx().resetDisplacement();
		Robot.drive.setLockAngle(navx.getYaw());
		Robot.drive.setTransX(-0.5);
		Robot.drive.setTransY(0);
	}

	public void execute() {
		AHRS navx = Robot.drive.getNavx();

		Robot.drive.getFrontLeft().setInverted(true);
		Robot.drive.getRearLeft().setInverted(true);

		double rotation = Robot.drive.getTurnController().getOutput(Robot.drive.getNavx().getYaw(), Robot.drive.getLockAngle());

		double corrX = Robot.drive.getTransControllerX().getOutput(navx.getVelocityY(), Robot.drive.getTransX());
		double corrY = Robot.drive.getTransControllerY().getOutput(navx.getVelocityX(), Robot.drive.getTransY());

		double movX = Robot.drive.getTransX() + corrX;
		double movY = Robot.drive.getTransY() + corrY;
		if (Math.hypot(movX, movY) > 1) {
			movX /= Math.hypot(movX, movY);
			movY /= Math.hypot(movX, movY);
		}

		Robot.drive.getDrive().mecanumDrive_Cartesian(movX, movY, rotation, 0);

		NetworkTable.getTable("jsDashboard").putNumber("frontLeft", navx.getVelocityX());
		NetworkTable.getTable("jsDashboard").putNumber("rearLeft", navx.getVelocityY());
		NetworkTable.getTable("jsDashboard").putNumber("frontRight", -0.5);

		NetworkTable.getTable("dataTable").putNumber("vx", navx.getVelocityX());
		NetworkTable.getTable("dataTable").putNumber("vy", navx.getVelocityY());
		NetworkTable.getTable("dataTable").putNumber("cx", corrX);
		NetworkTable.getTable("dataTable").putNumber("cy", corrY);
	}

	protected boolean isFinished() {
		return false;
	}

}
