package org.usfirst.frc.team1124.robot.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class BeUseless extends CommandGroup {
	public BeUseless() {
		this.addSequential(new HoldComponent(100));
	}
}
