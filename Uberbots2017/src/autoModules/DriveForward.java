package autoModules;

import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import utils.MiniPID;

public class DriveForward extends Command {
	MiniPID controller;

    public DriveForward(double dist) {
        requires(Robot.drive);
        controller = new MiniPID(0.1,0.001,0.1);
        controller.setOutputLimits(1.0);
        controller.setSetpoint(dist);
    }
    protected void initialize() {
    	Robot.drive.getNavx().reset();
    	Robot.drive.setTurnPoint(Robot.drive.getNavx().getYaw());
    }
    protected void execute() {
    	double actual = (-Robot.drive.getFrontLeft().getEncPosition() + -Robot.drive.getFrontRight().getEncPosition() + Robot.drive.getRearLeft().getEncPosition() + Robot.drive.getRearRight().getEncPosition())/4;
    	Robot.drive.getDrive().mecanumDrive_Cartesian(0, controller.getOutput(actual), Robot.drive.getTurnController().getOutput(Robot.drive.getNavx().getYaw(), Robot.drive.getTurnPoint()),0);
    }
    protected boolean isFinished() { return false; }
    protected void end() {}
    protected void interrupted() {}
}
