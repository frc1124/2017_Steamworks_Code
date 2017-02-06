package org.usfirst.frc.team1124.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearDoor extends Subsystem {
	private Solenoid gearDoor = new Solenoid(0, 0);
	
	public GearDoor() {}
	
    public void initDefaultCommand() {}
    public void toggle() { gearDoor.set(!gearDoor.get()); }
}

