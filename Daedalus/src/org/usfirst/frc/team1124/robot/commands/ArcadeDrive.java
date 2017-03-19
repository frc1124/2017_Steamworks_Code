package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.RobotMap;
import org.usfirst.frc.team1124.robot.subsystems.Drive;
import edu.wpi.first.wpilibj.networktables.*;

import edu.wpi.first.wpilibj.command.Command;

public class ArcadeDrive extends Command {

	private double leftX, leftY, rightX, rightY;

    public ArcadeDrive() { this.requires(Robot.chassis); }

	protected void initialize() {}

	protected void execute() {
		leftX = Math.pow(OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftX), 3);
		rightX = Math.pow(OI.firstDriver.getRawAxis(RobotMap.firstDriverRightX), 3);
		leftY = Math.pow(OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftY), 3);
		rightY = Math.pow(OI.firstDriver.getRawAxis(RobotMap.firstDriverRightY), 3);
    	NetworkTable.getTable("debug").putNumber("Joystick X", OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftX));
    	
		if ((Math.abs(leftX) > 0.02 || Math.abs(leftY) > 0.02) && (Math.abs(rightY) > 0.02 || Math.abs(rightX) > 0.02)) hybrid();
    	else if(Math.abs(leftX) > 0.02 || Math.abs(leftY) > 0.02) arcade();
    	else if(Math.abs(rightY) > 0.02 || Math.abs(rightX) > 0.02) mecanum(true);
    }
    protected boolean isFinished() { return(false); }
    protected void end() {}
    protected void interrupted() {}//this.cancel(); }
	private void arcade() {
		Robot.chassis.setRightSpeed(-leftY - leftX);
		Robot.chassis.setLeftSpeed(-leftY + leftX);
	}
    private void mecanum(boolean reset) {
    	if(reset) { Drive.lockAngle(); }
    	double rotation = Robot.chassis.turnController.getOutput(Drive.navx.getYaw(), Drive.lockAngle);
    	Drive.mec.mecanumDrive_Cartesian(rightX, rightY, rotation, 0);
    }
    
    private void hybrid() {
    	double frontLeft, rearLeft, frontRight, rearRight;
        double arcadeLeft, arcadeRight;

        double angleMec = Math.atan2(rightY, rightX);

        frontLeft = (Math.sin(Math.toRadians(angleMec)) + rightX) / 2;
        frontRight = (Math.cos(Math.toRadians(angleMec)) - rightX) / 2;
        rearLeft = (Math.cos(Math.toRadians(angleMec)) + rightX) / 2;
        rearRight = (Math.sin(Math.toRadians(angleMec)) - rightX) / 2;

        arcadeLeft = -leftY+leftX;
        arcadeRight = -leftY-leftX;

        frontLeft += (arcadeLeft / 2);
        frontRight += (arcadeRight / 2);
        rearLeft += (arcadeLeft / 2);
        rearRight += (arcadeRight / 2);

        Drive.leftBack.set(rearLeft);
        Drive.leftFront.set(frontLeft);
        Drive.rightBack.set(rearRight);
        Drive.rightFront.set(frontRight);
    }
}
