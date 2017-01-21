package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.RobotMap;
import org.usfirst.frc.team1124.robot.commands.TeleopDrive;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Drive extends Subsystem {

	NetworkTable table;

	private static final double PID_BUFFER = 0.5;

	private CANTalon wheelOne;
	private CANTalon wheelTwo;
	private CANTalon wheelThree;
	private CANTalon wheelFour;
	private RobotDrive robotDrive;

	public Drive() {

		table = NetworkTable.getTable("dataTable");

		wheelOne = new CANTalon(RobotMap.FRONT_LEFT);
		wheelTwo = new CANTalon(RobotMap.BACK_LEFT);
		wheelThree = new CANTalon(RobotMap.FRONT_RIGHT);
		wheelFour = new CANTalon(RobotMap.BACK_RIGHT);

		wheelOne.setEncPosition(0);
		wheelTwo.setEncPosition(0);
		wheelThree.setEncPosition(0);
		wheelFour.setEncPosition(0);

		robotDrive = new RobotDrive(wheelOne, wheelTwo, wheelThree, wheelFour);
		robotDrive.setSafetyEnabled(true);
		robotDrive.setExpiration(0.1);
		robotDrive.setMaxOutput(1.0);
		robotDrive.setSensitivity(0.5);

	}

	public void initDefaultCommand() {
		// by default, arcade drive is used
		setDefaultCommand(new TeleopDrive());
	}

	public RobotDrive getRobotDrive() {
		// returns the drive
		return robotDrive;
	}

	public void setSpeedOne(double x) {
		// sets the speed to front left wheel
		this.wheelOne.set(x);
	}

	public void setSpeedTwo(double x) {
		// sets the speed to front right wheel
		this.wheelTwo.set(x);
	}

	public void setSpeedThree(double x) {
		// sets the speed to back left wheel
		this.wheelThree.set(x);
	}

	public void setSpeedFour(double x) {
		// sets the speed to back right wheel
		this.wheelFour.set(x);
	}

	public void mechDrive(double dir, double mag) {

		double a = Math.sin(Math.toRadians(dir - 45));
		double b = Math.cos(Math.toRadians(dir - 45));
		a *= mag * PID_BUFFER;
		b *= mag * PID_BUFFER;
		setSpeedOne(-b);
		setSpeedTwo(-a);
		setSpeedThree(a);
		setSpeedFour(b);
	}

	public void putDataOnTable() {
		table.putNumber("back_left", wheelTwo.getEncPosition());
		table.putNumber("front_left", wheelOne.getEncPosition());
		table.putNumber("back_right", wheelFour.getEncPosition());
		table.putNumber("front_right", wheelThree.getEncPosition());

		table.putNumber("left_x", OI.stick.getX());
		table.putNumber("left_y", -OI.stick.getY());
		table.putNumber("right_x", OI.stick.getRawAxis(4));
		table.putNumber("right_y", -OI.stick.getRawAxis(5));
	}
}
