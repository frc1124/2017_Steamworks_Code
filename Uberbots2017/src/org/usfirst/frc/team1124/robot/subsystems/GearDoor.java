package org.usfirst.frc.team1124.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearDoor extends Subsystem {
	private DoubleSolenoid gearDoor = new DoubleSolenoid(0, 0, 1);
	
	public GearDoor() {}
	
    public void initDefaultCommand() {}
    public void toggle() { gearDoor.set((gearDoor.get()==Value.kForward) ? Value.kReverse : Value.kForward); }
}

