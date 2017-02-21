package org.usfirst.frc.team1124.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class Terminate extends Command {

	private Command cmdGroup;

	public Terminate(Command cmdGroup) {
		this.cmdGroup = cmdGroup;
	}

	public void initialize() {
		cmdGroup.cancel();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
