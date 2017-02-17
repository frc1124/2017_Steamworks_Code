package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class AutoQueue extends Command {

	private boolean done;

	private Command[] commands = new Command[11];

	int currentCommand = -1;

	protected boolean isFinished() {
		return done;
	}

	public AutoQueue() {
		
		commands[0] = new DriveForward(50);
		commands[1] = new Turn(90);
		commands[2] = new DriveForward(50);
		commands[3] = new Turn(90);
		commands[4] = new DriveForward(50);
		commands[5] = new Turn(90);
		commands[6] = new DriveForward(50);
		commands[7] = new Turn(135);
		commands[8] = new DriveForward(50 * Math.sqrt(2));
		commands[9] = new Turn(-45);
		commands[10] = new DriveForward(-50);
	}

	public void initialize() {
		Robot.drive.lockAngle = Robot.drive.navx.getYaw();
		currentCommand = -1;
		done = false;
	}

	public void execute() {
		if (done)
			return;
		if (currentCommand == -1 || !commands[currentCommand].isRunning()) {
			currentCommand++;
			if (currentCommand == commands.length) {
				done = true;
				return;
			}
			commands[currentCommand].start();
		}
		NetworkTable.getTable("queue").putNumber("commandNumber", currentCommand);
		NetworkTable.getTable("queue").putNumber("lockAngle", Robot.drive.lockAngle);
	}

}
