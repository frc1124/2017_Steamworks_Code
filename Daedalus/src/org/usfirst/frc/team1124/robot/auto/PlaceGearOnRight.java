package org.usfirst.frc.team1124.robot.auto;

import org.usfirst.frc.team1124.robot.commands.ToggleGearDoor;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class PlaceGearOnRight extends CommandGroup {
    public PlaceGearOnRight() {
    	//this.setInterruptible(false);
    	this.addSequential(new DriveComponent(47));
    	System.out.println("Drove 47");
		this.addSequential(new TurnComponent(60));
		System.out.println("Turned 60");
		//this.addSequential(new PressToWin());
		this.addSequential(new TargetComponent(), 2);
		System.out.println("Targeted");
		this.addSequential(new DriveComponent(30));
		System.out.println("Drove 30");
		this.addSequential(new ToggleGearDoor());
		System.out.println("Gear door up");
		this.addSequential(new WaitCommand(.75d));
		System.out.println("Waited 750 milliseconds");
		this.addSequential(new DriveComponent(-36));
		System.out.println("Drove 36");
		this.addSequential(new ToggleGearDoor());
		System.out.println("Gear door down");
		this.addSequential(new TurnComponent(-60));
		System.out.println("Turned 60");
		this.addSequential(new DriveComponent(30));
		System.out.println("Drove 30");
    }
    
    protected void end(){
    	System.out.println("ended");
    }
}