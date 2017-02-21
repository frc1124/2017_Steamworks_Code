package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class WaitForGear extends Command {
	private boolean done = false;

	public WaitForGear() {
		requires(Robot.gearDoor);
	}

	protected void execute() {
		if (Robot.gearDoor.hasGear()) {
			done = true;
		}
	}
	
	@Override
	protected boolean isFinished() {
		return done;
	}

}
