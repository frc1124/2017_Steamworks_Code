package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearDoor extends Subsystem {
	public static DoubleSolenoid gearDoor = new DoubleSolenoid(RobotMap.gearDoor[0],RobotMap.gearDoor[1],RobotMap.gearDoor[2]);
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void toggle() { this.set(gearDoor.get().equals(Value.kForward)); }
    public void set(boolean up) { gearDoor.set(up ? Value.kReverse : Value.kForward); }
}

