package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Thing extends CommandGroup {

    public Thing() {
    	Robot.drive.strafeDist = 0.0;
        this.addParallel(new TargetVisionTape(), 5);
        this.addParallel(new Strafe(), 5);
    }
}
