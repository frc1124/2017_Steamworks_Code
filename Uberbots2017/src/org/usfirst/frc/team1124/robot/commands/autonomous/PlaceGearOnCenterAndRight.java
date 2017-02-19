package org.usfirst.frc.team1124.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1124.robot.commands.*;

public class PlaceGearOnCenterAndRight extends CommandGroup {
	public PlaceGearOnCenterAndRight(){
		this.addSequential(new PlaceGearOnCenter());
		this.addSequential(new Strafe(80));
		this.addSequential(new DriveForward(470));
	}
}
