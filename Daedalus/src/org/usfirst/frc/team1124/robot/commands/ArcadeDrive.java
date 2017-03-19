package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.RobotMap;
import org.usfirst.frc.team1124.robot.subsystems.Drive;
import edu.wpi.first.wpilibj.networktables.*;

import edu.wpi.first.wpilibj.command.Command;

public class ArcadeDrive extends Command {

    public ArcadeDrive() { this.requires(Robot.chassis); }

    protected void initialize() {}
    protected void execute() {
    	NetworkTable.getTable("debug").putNumber("Joystick X", OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftX));
    	if( (Math.abs(OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftX))>0.02 
    			|| Math.abs(OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftY))>0.02) 
    			&& (Math.abs(OI.firstDriver.getRawAxis(RobotMap.firstDriverRightX))>0.02 
    			|| Math.abs(OI.firstDriver.getRawAxis(RobotMap.firstDriverRightY))>0.02) ) { hybrid(); }
    	
    	else if( (Math.abs(OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftX))>0.02 
    			|| Math.abs(OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftY))>0.02) ) { arcade(); }
    	
    	else if( (Math.abs(OI.firstDriver.getRawAxis(RobotMap.firstDriverRightX))>0.02 
    			|| Math.abs(OI.firstDriver.getRawAxis(RobotMap.firstDriverRightY))>0.02) ) { mecanum(true); }
    }
    protected boolean isFinished() { return(false); }
    protected void end() {}
    protected void interrupted() { }//this.cancel(); }
    private void arcade() {
    	Robot.chassis.setRightSpeed( Math.pow(-OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftY)-OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftX),3) );
    	Robot.chassis.setLeftSpeed( Math.pow(-OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftY)+OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftX),3) );
    }
    private void mecanum(boolean reset) {
    	if(reset) { Drive.lockAngle(); }
    	double x = Math.pow(OI.firstDriver.getRawAxis(RobotMap.firstDriverRightX), 3);
    	double y = Math.pow(OI.firstDriver.getRawAxis(RobotMap.firstDriverRightY), 3);
    	double rotation = Robot.chassis.turnController.getOutput(Drive.navx.getYaw(), Drive.lockAngle);
    	Drive.mec.mecanumDrive_Cartesian(x, y, rotation, 0);
    }
    
    private void hybrid() {
    	double frontLeft, rearLeft, frontRight, rearRight;
        double arcadeLeft, arcadeRight;

        double rx = Math.pow(OI.firstDriver.getRawAxis(RobotMap.firstDriverRightX), 3);
    	double ry = Math.pow(OI.firstDriver.getRawAxis(RobotMap.firstDriverRightY), 3);

        double angleMec = Math.atan2(ry, rx);

        frontLeft = (Math.sin(Math.toRadians(angleMec)) + rx);
        frontRight = (Math.cos(Math.toRadians(angleMec)) - rx);
        rearLeft = (Math.cos(Math.toRadians(angleMec)) + rx);
        rearRight = (Math.sin(Math.toRadians(angleMec)) - rx);

        arcadeLeft = (-OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftY))+OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftX);
        arcadeRight = (-OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftY))-OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftX);

        frontLeft += arcadeLeft;
        frontRight += arcadeRight;
        rearLeft += arcadeLeft;
        rearRight += arcadeRight;
        
        rearLeft = Math.max(Math.min(rearLeft, 1), -1);
        rearRight = Math.max(Math.min(rearRight, 1), -1);
        frontLeft = Math.max(Math.min(frontLeft, 1), -1);
        frontRight = Math.max(Math.min(frontRight, 1), -1);

        Drive.leftBack.set(rearLeft);
        Drive.leftFront.set(frontLeft);
        Drive.rightBack.set(rearRight);
        Drive.rightFront.set(frontRight);
    }
}
