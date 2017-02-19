package org.usfirst.frc.team1124.robot.subsystems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {
	private DoubleSolenoid ropeDoor = new DoubleSolenoid(0, 1, 3);
	private SpeedController climbMotor = new CANTalon(5);
	
	public Climber() {}
	
    public void initDefaultCommand() {}
    public void toggle() { ropeDoor.set((ropeDoor.get()==Value.kForward) ? Value.kReverse : Value.kForward); }
    public void motorUp() { climbMotor.set(1.0); }
    public void motorDown() { climbMotor.set(-1.0); }
    public void motorStop() { climbMotor.set(0.0); }
}

