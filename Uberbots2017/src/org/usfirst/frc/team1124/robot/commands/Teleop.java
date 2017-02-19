package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.OI;
import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class Teleop extends Command {
    private double leftX = 0.0;
    private double leftY = 0.0;
    private double rightX = 0.0;
    private double rightY = 0.0;

    public Teleop() { requires(Robot.drive); }

    protected void initilize() {}
    protected boolean isFinished() { return(false); }
    protected void end() {}
    protected void interuppted() {}
    protected void execute() {
        leftX = OI.stick.getRawAxis(0);
        leftY = -OI.stick.getRawAxis(1);
        rightX = OI.stick.getRawAxis(4);
        rightY = -OI.stick.getRawAxis(5);
        
        Robot.gearDoor.checkWall();

		if (Math.abs(rightX) >= 0.1 || Math.abs(rightY) >= 0.1) {
            if(Robot.drive.mode != 2) {
                Robot.drive.lockAngle();
                Robot.drive.mode = 2;
            }
            Robot.drive.run(rightX, rightY);
        }
        else {
            Robot.drive.mode = 1;
            Robot.drive.run(leftX, leftY);
        }
    }
}