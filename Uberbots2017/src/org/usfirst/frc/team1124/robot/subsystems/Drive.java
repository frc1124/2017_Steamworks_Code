package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.RobotMap;
import org.usfirst.frc.team1124.robot.commands.TeleopArcade;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive extends Subsystem {

	private static final double PID_BUFFER = 0.95;

	private SpeedController wheelOne;
	private SpeedController wheelTwo;
	private SpeedController wheelThree;
	private SpeedController wheelFour;
	private RobotDrive robotDrive;

	public Drive() {
		wheelOne = new CANTalon(RobotMap.FRONT_LEFT);
		wheelTwo = new CANTalon(RobotMap.FRONT_RIGHT);
		wheelThree = new CANTalon(RobotMap.BACK_LEFT);
		wheelFour = new CANTalon(RobotMap.BACK_RIGHT);

		robotDrive = new RobotDrive(wheelOne, wheelTwo, wheelThree, wheelFour);
		robotDrive.setSafetyEnabled(true);
		robotDrive.setExpiration(0.1);
		robotDrive.setMaxOutput(1.0);
		robotDrive.setSensitivity(0.5);
	}

	public void initDefaultCommand() {
		// by default, arcade drive is used
		setDefaultCommand(new TeleopArcade());
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
		System.out.println(((CANTalon) wheelOne).getEncPosition());
		System.out.println(((CANTalon) wheelTwo).getEncPosition());
		System.out.println(((CANTalon) wheelThree).getEncPosition());
		System.out.println(((CANTalon) wheelFour).getEncPosition());

		double a = Math.sin(Math.toRadians(dir - 45));
		double b = Math.cos(Math.toRadians(dir - 45));
		a *= mag * PID_BUFFER;
		b *= mag * PID_BUFFER;
		setSpeedOne(a);
		setSpeedTwo(b);
		setSpeedThree(b);
		setSpeedFour(a);
	}
}
