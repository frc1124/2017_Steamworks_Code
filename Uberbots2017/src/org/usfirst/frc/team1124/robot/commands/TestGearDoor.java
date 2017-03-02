package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class TestGearDoor extends Command {
	
	public TestGearDoor() {
		requires(Robot.gearDoor);
	}
	
	@Override
	protected void execute() {
		super.execute();
		if ((Robot.drive.leftMM() <= 300 && Robot.drive.rightMM() <= 300) && Robot.gearDoor.get() == Value.kReverse && (Robot.gearDoorDetect.getVoltage() < 2.5)) {
			Robot.gearDoor.set(Value.kForward);
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
