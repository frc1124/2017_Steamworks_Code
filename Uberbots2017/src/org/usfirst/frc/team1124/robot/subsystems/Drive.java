package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.RobotMap;
import org.usfirst.frc.team1124.robot.commands.Teleop;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive extends Subsystem{
	private final SpeedController wheelOne = RobotMap.wheelOne;
	private final SpeedController wheelTwo = RobotMap.wheelTwo;
	private final SpeedController wheelThree = RobotMap.wheelThree;
	private final SpeedController wheelFour = RobotMap.wheelFour;
	private final RobotDrive mechDrive = RobotMap.mechDrive;
	
	public void initDefaultCommand() {
		setDefaultCommand(new Teleop());
	}
}
