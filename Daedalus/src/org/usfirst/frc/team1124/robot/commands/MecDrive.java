package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class MecDrive extends Command {

    public MecDrive() { this.requires(Robot.chassis); }

    protected void initialize() {}
    @SuppressWarnings("static-access")
	protected void execute() {
    	double rot = 0.0;
    	Robot.chassis.mec.mecanumDrive_Cartesian( Math.pow(OI.firstDriver.getRawAxis(RobotMap.firstDriverRightX),2.2), Math.pow(OI.firstDriver.getRawAxis(RobotMap.firstDriverRightY),2.2), rot, 0 );
    }
    protected boolean isFinished() { return(false); }
    protected void end() {}
    protected void interrupted() { this.cancel(); }
}
