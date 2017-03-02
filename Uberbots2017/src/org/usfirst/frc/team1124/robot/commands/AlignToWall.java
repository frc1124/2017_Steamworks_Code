package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AlignToWall extends CommandGroup {
	private Turn turnCommand;

    public AlignToWall() {
    	turnCommand = new Turn(0);
    	addSequential(turnCommand);
    }
    protected void initialize() {
    	//turnCommand.setDegrees(-1*Robot.drive.calcAngle());
    	Robot.modeFlag = 7;
    }
    protected void end(){
    	Robot.modeFlag = 0;
    }
}
