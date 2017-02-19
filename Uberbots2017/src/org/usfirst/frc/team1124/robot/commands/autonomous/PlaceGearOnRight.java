package org.usfirst.frc.team1124.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1124.robot.commands.*;

public class PlaceGearOnRight extends CommandGroup {
	public PlaceGearOnRight(){
		this.addSequential(new DriveForward(129));
		this.addSequential(new Turn(-60));
		this.addSequential(new PressToWin());
		this.addSequential(new DriveForward(-12));
		this.addSequential(new Turn(60));
		this.addSequential(new DriveForward(341));
	}
}
