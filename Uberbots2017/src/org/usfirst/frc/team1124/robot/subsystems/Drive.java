package org.usfirst.frc.team1124.robot.subsystems;

import static org.usfirst.frc.team1124.robot.RobotMap.D;
import static org.usfirst.frc.team1124.robot.RobotMap.FRONT_LEFT;
import static org.usfirst.frc.team1124.robot.RobotMap.FRONT_RIGHT;
import static org.usfirst.frc.team1124.robot.RobotMap.I;
import static org.usfirst.frc.team1124.robot.RobotMap.INVERTED;
import static org.usfirst.frc.team1124.robot.RobotMap.P;
import static org.usfirst.frc.team1124.robot.RobotMap.REAR_LEFT;
import static org.usfirst.frc.team1124.robot.RobotMap.REAR_RIGHT;
import static org.usfirst.frc.team1124.robot.RobotMap.TRANS_PID;
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

	// Set points for the turnController and transAngleController
	private double wantedYaw = 0;
	private double wantedDirection = 0;

	// PIDs for the rotation and translation of the robot
	private MiniPID yawController = new MiniPID(P[TURN_PID], I[TURN_PID], D[TURN_PID]);
	private MiniPID directionController = new MiniPID(P[TRANS_PID], I[TRANS_PID], D[TRANS_PID]);

	// The NavX
	private AHRS navX = new AHRS(SPI.Port.kMXP);

	private CANTalon[] wheels = new CANTalon[5];

	private RobotDrive drive;

	public Drive() {

		// Setting the output limits
		yawController.setOutputLimits(1.0);
		directionController.setOutputLimits(1.0);

		// Initiating all of the wheels
		for (int i = 1; i <= 4; i++) {
			wheels[i] = new CANTalon(i);
			wheels[i].setFeedbackDevice(FeedbackDevice.QuadEncoder);
			wheels[i].setPID(P[i], I[i], D[i]);
			wheels[i].setF(5);
			wheels[i].changeControlMode(CANTalon.TalonControlMode.PercentVbus);
			wheels[i].configEncoderCodesPerRev(256);
			wheels[i].configMaxOutputVoltage(24);
			wheels[i].setInverted(INVERTED[i]);
		}

		// Initiating the actual drive train
		drive = new RobotDrive(wheels[FRONT_LEFT], wheels[REAR_LEFT], wheels[FRONT_RIGHT], wheels[REAR_RIGHT]);
	}

	public void initDefaultCommand() {
		this.setDefaultCommand(Robot.teleopDrive);
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

	public MiniPID getYawController() {
		return yawController;
	}

	public RobotDrive getDrive() {
		return drive;
	}

	public AHRS getNavx() {
		return navX;
	}

	public double getWantedYaw() {
		return wantedYaw;
	}

	public void setWantedYaw(double yaw) {
		this.wantedYaw = yaw;
	}

	public MiniPID getDirectionController() {
		return directionController;
	}

	public double getWantedDirection() {
		return wantedDirection;
	}

	public void setWantedDirection(double direction) {
		this.wantedDirection = direction;
	}

}
