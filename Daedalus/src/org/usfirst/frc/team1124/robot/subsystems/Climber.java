package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.RobotMap;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {
	public static CANTalon motor = new CANTalon(RobotMap.climber);
	public static DigitalInput limit = new DigitalInput(RobotMap.limit);
	public static DoubleSolenoid sol = new DoubleSolenoid(RobotMap.gearDoor[0],RobotMap.gearDoor[1],RobotMap.gearDoor[2]);
	
    public void initDefaultCommand() {}
    public void climb(boolean up) { motor.set((up&&limit.get()) ? 1.0 : -0.7); }
    public void limitOverride(boolean up) { motor.set(up ? 1.0 : -0.7); }
    public void allStop() { motor.set(0.0); }
    public void toggle() { this.set(sol.get().equals(Value.kForward)); }
    public void set(boolean up) { sol.set(up ? Value.kReverse : Value.kForward); }
}

