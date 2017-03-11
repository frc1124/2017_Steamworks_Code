package org.usfirst.frc.team1124.robot.commands.autonomous;

import org.usfirst.frc.team1124.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PlaceGearOnCenterAndLeft extends CommandGroup {
	public PlaceGearOnCenterAndLeft(){
		this.addSequential(new PlaceGearOnCenter());
		this.addSequential(new Strafe(-80));
		this.addSequential(new DriveForward(300));
	}
}
