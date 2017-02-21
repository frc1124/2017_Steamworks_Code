package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class RaiseGearDoor extends Command {
	private boolean done = false;

	public RaiseGearDoor() {
		requires(Robot.gearDoor);
	}

	protected void execute() {
		Robot.gearDoor.set(Value.kReverse);
		done = true;
	}

	@Override
	protected boolean isFinished() {
		return done;
	}

}
