package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.RobotMap;
import org.usfirst.frc.team1124.robot.commands.TeleopArcade;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive extends Subsystem {
	
	private SpeedController wheelOne;
	private SpeedController wheelTwo;
	private SpeedController wheelThree;
	private SpeedController wheelFour;
	private RobotDrive mechDrive;

	public Drive() {
		wheelOne = new Talon(RobotMap.FRONT_LEFT);
		wheelTwo = new Talon(RobotMap.FRONT_RIGHT);
		wheelThree = new Talon(RobotMap.BACK_LEFT);
		wheelFour = new Talon(RobotMap.BACK_LEFT);

		mechDrive = new RobotDrive(wheelOne, wheelTwo, wheelThree, wheelFour);
		mechDrive.setSafetyEnabled(true);
		mechDrive.setExpiration(0.1);
		mechDrive.setMaxOutput(1.0);
		mechDrive.setSensitivity(0.5);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new TeleopArcade());
	}
}
