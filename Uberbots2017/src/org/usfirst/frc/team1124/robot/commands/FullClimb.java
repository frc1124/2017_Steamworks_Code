package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class FullClimb extends Command {
	public FullClimb() {
		requires(Robot.climber);
	}

	protected void execute() {
		// If the door is open, close it
		if (Robot.climber.isDoorOpen()) {
			Robot.climber.setDoorOpen(true);
		}

		// If the limit switch is pressed, go; otherwise stop
		if (Robot.climber.isSwitchPressed()) {
			Robot.climber.motorUp();
		} else {
			Robot.climber.motorStop();
		}
	}

	protected void interrupted() {
		Robot.climber.motorStop();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
