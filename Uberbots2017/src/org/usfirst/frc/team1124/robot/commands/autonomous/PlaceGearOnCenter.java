package org.usfirst.frc.team1124.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1124.robot.commands.*;

public class PlaceGearOnCenter extends CommandGroup {
	public PlaceGearOnCenter(){
		this.addSequential(new PressToWin());
		this.addSequential(new DriveForward(-12));
	}
}
