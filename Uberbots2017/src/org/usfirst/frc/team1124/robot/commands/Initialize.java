package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class Initialize extends Command {
	private boolean done = false;
	private Compressor compressor;

	public Initialize() {
		requires(Robot.drive);
		requires(Robot.gearDoor);
		requires(Robot.climber);
	}

	protected void initialize() {
		compressor = new Compressor(0);

		// Make sure the motors are stopped
		Robot.drive.frontLeft.set(0);
		Robot.drive.frontRight.set(0);
		Robot.drive.rearLeft.set(0);
		Robot.drive.rearRight.set(0);
	}

	protected void execute() {
		// Wait for the air pressure to come up and set the pneumatics
		if (!compressor.getPressureSwitchValue()) {
			// Raise the gear door
			Robot.gearDoor.set(Value.kReverse);

			// Open the climber door
			Robot.climber.setDoorOpen(true);

			// Let the command finish
			done = true;
		}
	}
	
	@Override
	protected boolean isFinished() {
		return done;
	}

}