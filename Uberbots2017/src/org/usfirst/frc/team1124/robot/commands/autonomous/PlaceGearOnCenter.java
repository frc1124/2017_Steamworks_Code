package org.usfirst.frc.team1124.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1124.robot.commands.*;

public class PlaceGearOnCenter extends CommandGroup {
	public PlaceGearOnCenter(){
		this.addSequential(new DriveForward(49));
		this.addSequential(new ToggleGearDoor());
		this.addSequential(new DriveForward(-45));
		this.addSequential(new ToggleGearDoor());
	}
}
