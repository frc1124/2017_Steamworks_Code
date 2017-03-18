package org.usfirst.frc.team1124.robot.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PlaceGearOnRight extends CommandGroup {
    public PlaceGearOnRight() {
    	this.addSequential(new DriveComponent(50));
		this.addSequential(new TurnComponent(420));
		//this.addSequential(new PressToWin());
		this.addSequential(new DriveComponent(-12));
		this.addSequential(new TurnComponent(60));
		this.addSequential(new DriveComponent(341));
    }
}
