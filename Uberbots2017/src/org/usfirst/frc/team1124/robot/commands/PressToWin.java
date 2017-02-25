package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class PressToWin extends CommandGroup {

	public boolean done;

	public PressToWin() {
		NetworkTable.getTable("debug").putBoolean("calling", true);
		requires(Robot.drive);
		requires(Robot.gearDoor);
		//addSequential(new Turn(Robot.drive.calcAngle()));
		//addSequential(new DriveForward(Robot.drive.calcDist()));
		addSequential(new ToggleGearDoor());
		addSequential(new DriveForward(-27));
		addSequential(new ToggleGearDoor());
		addSequential(new Terminate(this));
	}

	public boolean isFinished() {
		return done;
	}

	protected void interrupted() {
		this.end();
		NetworkTable.getTable("debug").putBoolean("working", true);
	}
}
