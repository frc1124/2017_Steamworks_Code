package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class ArcadeDrive extends Command {

    public ArcadeDrive() { this.requires(Robot.chassis); }

    protected void initialize() {}
    protected void execute() {
    	Robot.chassis.setRightSpeed(-OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftY)-OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftX));
    	Robot.chassis.setLeftSpeed(-OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftY)+OI.firstDriver.getRawAxis(RobotMap.firstDriverLeftX));
    }
    protected boolean isFinished() { return(false); }
    protected void end() {}
    protected void interrupted() { this.cancel(); }
}
