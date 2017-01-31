package org.usfirst.frc.team1124.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Pneumatics extends Subsystem {
	private Solenoid gearDoor = new Solenoid(0, 0);
	private Solenoid ropeDoor = new Solenoid(0, 1);
	
    public void initDefaultCommand() {}
    public void toggleGearDoor() { gearDoor.set( !gearDoor.get() ); }
    public void toggleRopeDoor() { ropeDoor.set( !ropeDoor.get() ); }
}

