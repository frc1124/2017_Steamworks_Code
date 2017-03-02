package org.usfirst.frc.team1124.robot.commands.autonomous;

import org.usfirst.frc.team1124.robot.commands.DriveForward;
import org.usfirst.frc.team1124.robot.commands.Strafe;
import org.usfirst.frc.team1124.robot.commands.Turn;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PlaceGearOnCenterAndRight extends CommandGroup {
	public PlaceGearOnCenterAndRight(){
		//this.addSequential(new PlaceGearOnCenter());
		this.addSequential(new Turn(270));
		this.addSequential(new DriveForward(60));
		this.addSequential(new Turn(90));
		this.addSequential(new DriveForward(470));
	}
}
