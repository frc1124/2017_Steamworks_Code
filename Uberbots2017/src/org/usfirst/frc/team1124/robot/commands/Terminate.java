package org.usfirst.frc.team1124.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class Terminate extends Command {

	private PressToWin pressToWin;

	public Terminate(PressToWin pressToWin) {
		this.pressToWin = pressToWin;
	}

	public void intialize() {
		pressToWin.done = true;
		pressToWin.cancel();
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
