package org.usfirst.frc.team1124.robot.auto;

import org.usfirst.frc.team1124.robot.commands.ToggleGearDoor;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class PlaceGearOnCenter extends CommandGroup {
	public PlaceGearOnCenter(){
		this.addSequential(new DriveComponent(48));
		this.addSequential(new ToggleGearDoor());
		this.addSequential(new HoldComponent(1000));
		this.addSequential(new DriveComponent(-45));
		this.addSequential(new ToggleGearDoor());
	}
}