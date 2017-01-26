package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;
import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import utils.MiniPID;

public class Drive extends Subsystem {
	private double turnPoint = 0.0;
	private MiniPID turn = new MiniPID(0.01, 0.006, 0.5);
	private AHRS navX = new AHRS(SPI.Port.kMXP);
	private static CANTalon frontLeft = new CANTalon(1);
	private static CANTalon rearLeft = new CANTalon(2);
	private static CANTalon frontRight = new CANTalon(3);
	private static CANTalon rearRight = new CANTalon(4);

	private RobotDrive drive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);

	public Drive() {
		turn.setOutputLimits(1.0);
	}

	public static CANTalon getFrontLeft() {
		return frontLeft;
	}

	public static CANTalon getFrontRight() {
		return frontRight;
	}

	public static CANTalon getRearLeft() {
		return rearLeft;
	}

	public static CANTalon getRearRight() {
		return rearRight;
	}

	public void setTurnPoint(double point) {
		this.turnPoint = point;
	}

	public double getTurnPoint() {
		return turnPoint;
	}

	public MiniPID getTurn() {
		return turn;
	}

	public RobotDrive getDrive() {
		return drive;
	}

	public AHRS getNavx() {
		return navX;
	}

	public void initDefaultCommand() {
		this.setDefaultCommand(Robot.teleopDrive);
	}
}
