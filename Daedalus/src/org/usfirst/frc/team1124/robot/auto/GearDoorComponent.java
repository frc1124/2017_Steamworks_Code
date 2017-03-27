package org.usfirst.frc.team1124.robot.auto;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

//Raw gear door toggle
//Please, god, let this work
public class GearDoorComponent extends Command {

	private boolean up;
	
	//Default constructor switches between up and down
	public GearDoorComponent(){
		this.up = !Robot.gearDoor.get();
		System.out.println("Gear Door constructed");
		this.setInterruptible(false);
	}
	
	//Overloaded constructor manually switches the value 
	public GearDoorComponent(boolean up) {
		this.up = up;
		System.out.println("Gear Door constructed");
		this.setInterruptible(false);
	}
	
	@Override
	protected void execute() {
		super.execute();
		Robot.gearDoor.set(up);
		System.out.println("Gear Door Executed");
	}

	@Override
	protected boolean isFinished() {
		System.out.println("Gear Door Finished");
		return true;
	}

}
