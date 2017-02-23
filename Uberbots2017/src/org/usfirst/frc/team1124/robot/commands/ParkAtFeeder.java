package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ParkAtFeeder extends CommandGroup {
	public ParkAtFeeder() {
		addParallel(new ToggleGearDoor(true));
		addSequential(new AlignToWall());
		addSequential(new DriveForward(Robot.drive.calcDist()));
		addSequential(new TargetVisionTape());
		// If indicating ready with lights, add cmd to set here
	}
}
