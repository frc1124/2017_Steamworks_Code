package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ParkAtFeeder extends CommandGroup {
	private boolean done = false;
	
	public ParkAtFeeder() {
		requires(Robot.drive);
		requires(Robot.gearDoor);
		addParallel(new RaiseGearDoor());
		addSequential(new Turn(Robot.drive.calcAngle()));
		addSequential(new DriveForward(Robot.drive.calcDist()));
		addSequential(new TargetVisionTape());
		addSequential(new WaitForGear());
		addSequential(new Terminate(this));
	}

	public void initialize() {
		done = false;
	}

	public boolean isFinished() {
		return done;
	}

	public void cancel() {
		done = true;
	}
}
