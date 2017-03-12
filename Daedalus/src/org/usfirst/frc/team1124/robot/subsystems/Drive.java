package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.RobotMap;
import org.usfirst.frc.team1124.robot.commands.TankDrive;
import org.usfirst.frc.team1124.robot.utils.MiniPID;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class Drive extends Subsystem {
	//components
	public static CANTalon leftFront = new CANTalon(RobotMap.leftFront);
	public static CANTalon leftBack = new CANTalon(RobotMap.leftBack);
	public static CANTalon rightFront = new CANTalon(RobotMap.rightFront);
	public static CANTalon rightBack = new CANTalon(RobotMap.rightBack);
	public static Gyro gyro = new AnalogGyro(RobotMap.gyro);
	public static MiniPID speedPID = new MiniPID(0.01,0.00,0.01).setOutputLimits(1);
    
	public Drive() {
		leftFront.changeControlMode(CANTalon.TalonControlMode.Speed);
		leftBack.changeControlMode(CANTalon.TalonControlMode.Speed);
		rightFront.changeControlMode(CANTalon.TalonControlMode.Speed);
		rightBack.changeControlMode(CANTalon.TalonControlMode.Speed);
		
		leftFront.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		leftBack.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rightFront.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rightBack.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		
		leftFront.setPID(0.01, 0.0, 0.01);
		leftBack.setPID(0.01, 0.0, 0.01);
		rightFront.setPID(0.01, 0.0, 0.01);
		rightBack.setPID(0.01, 0.0, 0.01);
	}
	
	public void allStop() {
		leftFront.set(0.0);
		leftBack.set(0.0);
		rightFront.set(0.0);
		rightBack.set(0.0);
	}
	public void setLeftSpeed(double spd) {
		leftFront.set(spd);
		leftBack.set(spd);
	}
	public void setRightSpeed(double spd) {
		rightFront.set(speedPID.getOutput(this.getRightSpeed(), spd));
		rightBack.set(speedPID.getOutput(this.getRightSpeed(), spd));
	}
	public void strafe(double spd) {
		double rot = 0.0;
		leftFront.set(spd+rot);
		leftBack.set(-spd+rot);
		rightFront.set(-spd-rot);
		rightBack.set(spd-rot);
	}
	public double getLeftSpeed() { return(0); }
	public double getRightSpeed() { return(0); }
    public void initDefaultCommand() { this.setDefaultCommand(new TankDrive()); }
}

