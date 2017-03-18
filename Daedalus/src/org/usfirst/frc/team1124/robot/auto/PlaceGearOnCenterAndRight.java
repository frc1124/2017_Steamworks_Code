package org.usfirst.frc.team1124.robot.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PlaceGearOnCenterAndRight extends CommandGroup {
	public PlaceGearOnCenterAndRight(){
		this.addSequential(new PlaceGearOnCenter());
		this.addSequential(new TurnComponent(-90));
		this.addSequential(new DriveComponent(50));
		this.addSequential(new TurnComponent(90));
		this.addSequential(new DriveComponent(470));
	}
}