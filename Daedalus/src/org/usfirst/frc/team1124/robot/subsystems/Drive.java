package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.RobotMap;
import org.usfirst.frc.team1124.robot.commands.ArcadeDrive;
import org.usfirst.frc.team1124.robot.utils.MiniPID;
import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive extends Subsystem {
	//components
	public static CANTalon leftFront = new CANTalon(RobotMap.leftFront);
	public static CANTalon leftBack = new CANTalon(RobotMap.leftBack);
	public static CANTalon rightFront = new CANTalon(RobotMap.rightFront);
	public static CANTalon rightBack = new CANTalon(RobotMap.rightBack);
	public static AHRS navx = new AHRS(Port.kMXP);
	public static MiniPID speedPID = new MiniPID(0.01,0.00,0.01).setOutputLimits(1);
	public static RobotDrive mec = new RobotDrive(leftFront, leftBack, rightFront, rightBack);
	public MiniPID turnController = new MiniPID(0.03, 0, 0.1).setOutputLimits(1);
	public static double lockAngle = 0.0;
    
	public Drive() {
		leftFront.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		leftBack.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		rightFront.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		rightBack.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		
		leftFront.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		leftBack.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rightFront.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rightBack.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		
		leftFront.setPID(0.01, 0.0, 0.01);
		leftBack.setPID(0.01, 0.0, 0.01);
		rightFront.setPID(0.01, 0.0, 0.01);
		rightBack.setPID(0.01, 0.0, 0.01);
		
		leftFront.setInverted(true);
		leftBack.setInverted(true);
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
		rightFront.set(spd);
		rightBack.set(spd);
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
	public static void lockAngle() { Drive.lockAngle = Drive.navx.getYaw(); }
    public void initDefaultCommand() { this.setDefaultCommand(new ArcadeDrive()); } //try tank drive as well
}

