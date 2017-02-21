package org.usfirst.frc.team1124.robot.subsystems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Climber extends Subsystem {
	private DoubleSolenoid ropeDoor = new DoubleSolenoid(0, 1, 3);
	private SpeedController climbMotor = new CANTalon(5);
	public AnalogInput limit = new AnalogInput(3);
	
	public Climber() {}
	
    public void initDefaultCommand() {}
    public void toggle() { ropeDoor.set((ropeDoor.get()==Value.kForward) ? Value.kReverse : Value.kForward); }
    public void motorUp() { 
    	
    	if(limit.getVoltage()>2) { climbMotor.set(1.0); } else { climbMotor.set(0); } 
    }
    public void motorDown() { climbMotor.set(-1.0); }
    public void motorStop() { climbMotor.set(0.0); }
	public boolean isDoorOpen() {
		return this.ropeDoor.get().equals(Value.kReverse);
	}

	public void setDoorOpen(boolean doorOpen) {
		ropeDoor.set((doorOpen) ? Value.kReverse : Value.kForward);
	}

	public boolean isSwitchPressed() {
		return (limit.getAverageVoltage() > 2);
	}
}

