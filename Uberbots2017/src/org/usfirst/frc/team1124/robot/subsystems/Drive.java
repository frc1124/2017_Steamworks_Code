package org.usfirst.frc.team1124.robot.subsystems;

import static org.usfirst.frc.team1124.robot.RobotMap.D;
import static org.usfirst.frc.team1124.robot.RobotMap.FRONT_LEFT;
import static org.usfirst.frc.team1124.robot.RobotMap.FRONT_RIGHT;
import static org.usfirst.frc.team1124.robot.RobotMap.I;
import static org.usfirst.frc.team1124.robot.RobotMap.P;
import static org.usfirst.frc.team1124.robot.RobotMap.REAR_LEFT;
import static org.usfirst.frc.team1124.robot.RobotMap.REAR_RIGHT;
import static org.usfirst.frc.team1124.robot.RobotMap.TURN_PID;

import org.usfirst.frc.team1124.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import utils.MiniPID;

public class Drive extends Subsystem {
	private double lockAngle = 0.0;
	private MiniPID turnController = new MiniPID(P[TURN_PID], I[TURN_PID], D[TURN_PID]);
	private AHRS navX = new AHRS(SPI.Port.kMXP);

	private CANTalon[] wheels = new CANTalon[5];

	private RobotDrive drive = new RobotDrive(wheels[FRONT_LEFT], wheels[REAR_LEFT], wheels[FRONT_RIGHT], wheels[REAR_RIGHT]);

	public Drive() {
		turnController.setOutputLimits(1.0);

		for (int i = 1; i <= 4; i++) {
			wheels[i].setFeedbackDevice(FeedbackDevice.QuadEncoder);
			wheels[i].setPID(P[i], I[i], D[i]);
			wheels[i].setF(5);
			wheels[i].changeControlMode(CANTalon.TalonControlMode.PercentVbus);
			wheels[i].configEncoderCodesPerRev(256);
			wheels[i].configMaxOutputVoltage(24);
		}
	}

	public CANTalon getFrontLeft() {
		return wheels[FRONT_LEFT];
	}

	public CANTalon getFrontRight() {
		return wheels[FRONT_RIGHT];
	}

	public CANTalon getRearLeft() {
		return wheels[REAR_LEFT];
	}

	public CANTalon getRearRight() {
		return wheels[REAR_RIGHT];
	}

	public double getLockAngle() {
		return lockAngle;
	}

	public MiniPID getTurnController() {
		return turnController;
	}

	public RobotDrive getDrive() {
		return drive;
	}

	public AHRS getNavx() {
		return navX;
	}

	public void setLockAngle(double angle) {
		this.lockAngle = angle;
	}

	public void initDefaultCommand() {
		this.setDefaultCommand(Robot.teleopDrive);
	}
}
