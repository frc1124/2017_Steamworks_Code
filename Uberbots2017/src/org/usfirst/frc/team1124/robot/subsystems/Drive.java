package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.OI;
import static org.usfirst.frc.team1124.robot.RobotMap.*;
import org.usfirst.frc.team1124.robot.commands.TeleopDrive;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Drive extends Subsystem {

	public static NetworkTable table;

	private static final double PID_BUFFER = 0.95;

	private CANTalon[] wheels = new CANTalon[5];
	private RobotDrive robotDrive;

	public AHRS navX;

	public Drive() {

		table = NetworkTable.getTable("dataTable");

		for (int i = 1; i <= 4; i++) {
			wheels[i] = new CANTalon(i);
			wheels[i].setEncPosition(0);
		}

		robotDrive = new RobotDrive(wheels[FRONT_LEFT], wheels[BACK_LEFT], wheels[FRONT_RIGHT], wheels[BACK_RIGHT]);
		robotDrive.setSafetyEnabled(true);
		robotDrive.setExpiration(0.1);
		robotDrive.setMaxOutput(1.0);
		robotDrive.setSensitivity(0.5);

		navX = new AHRS(Port.kMXP);
		navX.zeroYaw();

	}

	public void initDefaultCommand() {
		// by default, arcade drive is used
		setDefaultCommand(new TeleopDrive());
	}

	public RobotDrive getRobotDrive() {
		// returns the drive
		return robotDrive;
	}

	public void setSpeed(double s, int CANchannel) {
		wheels[CANchannel].set(s);
	}

	public void mechDrive(double dir, double mag) {

		if (OI.stick.getRawButton(6)) {
			dir = 90;
			mag = 1;
		}

		double a = Math.sin(Math.toRadians(dir - 45));
		double b = Math.cos(Math.toRadians(dir - 45));
		a *= mag * PID_BUFFER;
		b *= mag * PID_BUFFER;
		setSpeed(-b, FRONT_LEFT);
		setSpeed(-a, BACK_LEFT);
		setSpeed(a, FRONT_RIGHT);
		setSpeed(b, BACK_RIGHT);
	}

	public void putDataOnTable() {
		table.putNumber("back_left", wheels[BACK_LEFT].getEncPosition());
		table.putNumber("front_left", wheels[FRONT_LEFT].getEncPosition());
		table.putNumber("back_right", wheels[BACK_RIGHT].getEncPosition());
		table.putNumber("front_right", wheels[FRONT_RIGHT].getEncPosition());

		table.putNumber("left_x", OI.stick.getX());
		table.putNumber("left_y", -OI.stick.getY());
		table.putNumber("right_x", OI.stick.getRawAxis(4));
		table.putNumber("right_y", -OI.stick.getRawAxis(5));

		table.putNumber("Yaw", navX.getYaw());
	}
}
