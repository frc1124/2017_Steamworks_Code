package org.usfirst.frc.team1124.robot.auto;

import org.usfirst.frc.team1124.robot.commands.ToggleGearDoor;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class PlaceGearOnRight extends CommandGroup {
    public PlaceGearOnRight() {
    	this.addSequential(new DriveComponent(47));
		this.addSequential(new TurnComponent(60));
		//this.addSequential(new PressToWin());
		this.addSequential(new DriveComponent(36));
		this.addSequential(new ToggleGearDoor());
		this.addSequential(new WaitCommand(0.35f));
		this.addSequential(new DriveComponent(-36));
		this.addSequential(new ToggleGearDoor());
		this.addSequential(new TurnComponent(-60));
		this.addSequential(new DriveComponent(30));
    }
}