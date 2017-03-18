package org.usfirst.frc.team1124.robot.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PlaceGearOnLeft extends CommandGroup {
    public PlaceGearOnLeft() {
    	this.addSequential(new DriveComponent(129));
		this.addSequential(new TurnComponent(60));
		//this.addSequential(new TargetComponent());
		this.addSequential(new DriveComponent(-12));
		this.addSequential(new TurnComponent(-60));
		this.addSequential(new DriveComponent(341));
    }
}
