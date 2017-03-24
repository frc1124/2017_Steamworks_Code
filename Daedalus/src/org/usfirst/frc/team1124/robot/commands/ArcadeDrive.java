package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.RobotMap;
import org.usfirst.frc.team1124.robot.subsystems.Drive;
import edu.wpi.first.wpilibj.networktables.*;

import edu.wpi.first.wpilibj.command.Command;

public class ArcadeDrive extends Command {
	
	private static final int ARCADE_MODE = 0;
	private static final int MEC_MODE = 1;
	
	private int mode = ARCADE_MODE;

	private double leftX, leftY, rightX, rightY;

    public ArcadeDrive() { this.requires(Robot.chassis); }

	protected void initialize() {
		Drive.lockAngle();
	}

	protected void execute() {
		leftX = Math.pow(OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftX), 3);
		rightX = Math.pow(OI.firstDriver.getRawAxis(RobotMap.firstDriverRightX), 3);
		leftY = Math.pow(OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftY), 3);
		rightY = Math.pow(OI.firstDriver.getRawAxis(RobotMap.firstDriverRightY), 3);
    	NetworkTable.getTable("debug").putNumber("Joystick X", OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftX));
    	
    	if((OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftX) > 0.02) || OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftY) > 0.02) arcade();
    	else if(OI.firstDriver.getRawAxis(RobotMap.firstDriverRightX) > 0.02 || OI.firstDriver.getRawAxis(RobotMap.firstDriverRightY) > 0.02) mecanum();
    	else stayStill();
    }
    private void stayStill() {
		double rotation = 0;
		if (mode == MEC_MODE)
    		rotation = Robot.chassis.turnController.getOutput(Drive.navx.getYaw(), Drive.lockAngle);
    	Drive.mec.mecanumDrive_Cartesian(0, 0, rotation, 0);
	}

	protected boolean isFinished() { return(false); }
    protected void end() {}
    protected void interrupted() {}
	private void arcade() {
		mode = ARCADE_MODE;
		Robot.chassis.setRightSpeed(-leftY - leftX);
		Robot.chassis.setLeftSpeed(-leftY + leftX);
	}
    private void mecanum() {
    	if(mode==ARCADE_MODE) { Drive.lockAngle(); }
    	mode = MEC_MODE;
    	double rotation = Robot.chassis.turnController.getOutput(Drive.navx.getYaw(), Drive.lockAngle);
    	Drive.mec.mecanumDrive_Cartesian(rightX, rightY, rotation, 0);
    }
    
}
