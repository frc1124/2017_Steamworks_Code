package org.usfirst.frc.team1124.robot.commands;
import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class ToggleGearDoor extends Command {
	private Boolean raiseDoor = null;

	public ToggleGearDoor(boolean raiseDoor) {
    	this.raiseDoor = raiseDoor;
    }

    public ToggleGearDoor() {}
    
    protected void initialize() {
    	if (raiseDoor != null) {
    		Robot.gearDoor.set((raiseDoor) ? Value.kReverse : Value.kForward);
    	} else {
    		Robot.gearDoor.toggle();
    	}
    }
    protected void execute() {    }
    protected boolean isFinished() { return(true); }
    protected void end() {}
    protected void interrupted() {}
}
