package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class TestGearDoor extends Command {

	private DigitalInput optical = new DigitalInput(0);
	public TestGearDoor() {
		requires(Robot.gearDoor);
	}
	
	@Override
	protected void execute() {
		super.execute();
//		if ((Robot.drive.ultrasonic1MM() <= 400 && Robot.drive.ultrasonic2MM() <= 400) && Robot.gearDoor.get() == Value.kForward && !optical.get()) {
//			Robot.gearDoor.set(Value.kReverse);
//		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
