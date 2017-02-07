package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PressToWin extends CommandGroup {

    public PressToWin() {
    	requires(Robot.drive);
    	requires(Robot.gearDoor);
        addSequential(new TargetVisionTape());
        addSequential(new ToggleGearDoor());
        addSequential(new DriveForward(-27));
        addSequential(new ToggleGearDoor());
    }
}
