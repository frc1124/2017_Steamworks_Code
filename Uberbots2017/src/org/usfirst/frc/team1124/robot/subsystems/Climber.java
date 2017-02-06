package org.usfirst.frc.team1124.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {
	private Solenoid ropeDoor = new Solenoid(0, 1);
	
	public Climber() {}
	
    public void initDefaultCommand() {}
    public void toggle() { ropeDoor.set(!ropeDoor.get()); }
}

