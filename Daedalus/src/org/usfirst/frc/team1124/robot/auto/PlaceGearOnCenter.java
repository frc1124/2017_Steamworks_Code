package org.usfirst.frc.team1124.robot.auto;

import org.usfirst.frc.team1124.robot.commands.TargetVisionTape;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class PlaceGearOnCenter extends CommandGroup {
	public PlaceGearOnCenter(){
		this.addSequential(new DriveComponent(24));
		this.addSequential(new TargetVisionTape(), 1);
		this.addSequential(new DriveComponent(24));
		this.addSequential(new GearDoorComponent(false));
		this.addSequential(new WaitCommand(.75d));
		this.addSequential(new DriveComponent(-48));
		this.addSequential(new GearDoorComponent(true));
	}
}