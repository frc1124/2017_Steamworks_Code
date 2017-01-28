package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import utils.MiniPID;

public class Drive extends Subsystem {
	private double turnPoint = 0.0;
	private MiniPID turnController = new MiniPID(0.01, 0.006, 0.5);
	private AHRS navX = new AHRS(SPI.Port.kMXP);
	private CANTalon frontLeft = new CANTalon(1);
	private CANTalon rearLeft = new CANTalon(2);
	private CANTalon frontRight = new CANTalon(3);
	private CANTalon rearRight = new CANTalon(4);

	private RobotDrive drive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);

	public Drive() {
		turnController.setOutputLimits(1.0);
		
		frontLeft.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		frontLeft.setPID(1, 0, 0.1);
		frontLeft.setF(5);
		frontLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		frontLeft.configEncoderCodesPerRev(256);
		frontLeft.configMaxOutputVoltage(24);
		
		frontRight.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		frontRight.setPID(1, 0, 0.1);
		frontRight.setF(5);
		frontRight.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		frontRight.configEncoderCodesPerRev(256);
		frontLeft.configMaxOutputVoltage(24);

		rearLeft.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rearLeft.setPID(1, 0, 0.1);
		rearLeft.setF(5);
		rearLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		rearLeft.configEncoderCodesPerRev(256);
		frontLeft.configMaxOutputVoltage(24);
		
		rearRight.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rearRight.setPID(1, 0, 0.1);
		rearRight.setF(5);
		rearRight.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		rearRight.configEncoderCodesPerRev(256);
		frontLeft.configMaxOutputVoltage(24);
	}

	public CANTalon getFrontLeft() { return frontLeft; }
	public CANTalon getFrontRight() { return frontRight; }
	public CANTalon getRearLeft() { return rearLeft; }
	public CANTalon getRearRight() { return rearRight; }

	public double getTurnPoint() { return turnPoint; }
	public MiniPID getTurnController() { return turnController; }
	public RobotDrive getDrive() { return drive; }
	public AHRS getNavx() { return navX; }
	
	public void setTurnPoint(double point) { this.turnPoint = point; }

	public void initDefaultCommand() { this.setDefaultCommand(Robot.teleopDrive); }
}
