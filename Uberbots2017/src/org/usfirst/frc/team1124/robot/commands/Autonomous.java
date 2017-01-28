package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.command.Command;
import utils.TableManager;

public class Autonomous extends Command {

	double mag = 0.5;

	public Autonomous() {
		requires(Robot.drive);
	}

	public void initialize() {
		AHRS navx = Robot.drive.getNavx();

		Robot.drive.getNavx().reset();
		Robot.drive.getNavx().resetDisplacement();
		Robot.drive.setLockAngle(navx.getYaw());
		Robot.drive.setTransAngle(180);
	}

	public void execute() {
		AHRS navx = Robot.drive.getNavx();

		double rotation = Robot.drive.getTurnController().getOutput(Robot.drive.getNavx().getYaw(), Robot.drive.getLockAngle());

		double corr = Robot.drive.getTransAngleController().getOutput(Math.toDegrees(Math.atan2(navx.getVelocityX(), navx.getVelocityY())), Robot.drive.getTransAngle());

		double angleOfTrans = Robot.drive.getTransAngle();

		angleOfTrans += corr;
		angleOfTrans %= 360;
		angleOfTrans += 360;
		angleOfTrans %= 360;

		Robot.drive.getDrive().mecanumDrive_Cartesian(mag * Math.cos(Math.toRadians(angleOfTrans)), -mag * Math.sin(Math.toRadians(angleOfTrans)), rotation, 0);

		TableManager.put("jsDashboard", "frontLeft", Math.atan2(navx.getVelocityX(), navx.getVelocityY())*10);

		TableManager.put("dataTable", "forward", navx.getVelocityX());
		TableManager.put("dataTable", "right", navx.getVelocityY());
		TableManager.put("dataTable", "angle", angleOfTrans);
		TableManager.put("dataTable", "corr", corr);
	}

	protected boolean isFinished() {
		return false;
	}

}
