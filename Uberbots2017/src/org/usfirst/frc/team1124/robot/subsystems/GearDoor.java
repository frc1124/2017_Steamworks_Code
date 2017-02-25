package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.TestGearDoor;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearDoor extends Subsystem {
	private DoubleSolenoid gearDoor = new DoubleSolenoid(0, 0, 2);

	private DigitalInput optical = new DigitalInput(0);

	public GearDoor() {
		this.setDefaultCommand(new TestGearDoor());
	}

	public void initDefaultCommand() {
		gearDoor.set(Value.kReverse);
	}

	public void toggle() {
		gearDoor.set((gearDoor.get().equals(Value.kForward)) ? Value.kReverse : Value.kForward);
	}

	public void set(Value v) {
		gearDoor.set(v);
	}

	public Value get() {
		return gearDoor.get();
	}
}
