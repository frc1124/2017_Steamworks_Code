package org.usfirst.frc.team1124.robot;

import org.usfirst.frc.team1124.robot.commands.Auto;
import org.usfirst.frc.team1124.robot.commands.Teleop;
import org.usfirst.frc.team1124.robot.subsystems.Drive;
import org.usfirst.frc.team1124.robot.subsystems.Pneumatics;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {
    public static Drive drive = new Drive();
    public static Pneumatics pneumatics = new Pneumatics();
    public static OI oi = new OI();
    public static Command teleop = new Teleop();
    public static Command auto = new Auto();

    public void robotInit() { 
    	drive.navx.reset();
    	RobotMap.init(); 
    }

    public void disabledInit() {}
	public void autonomousInit() { auto.start(); }
	public void teleopInit() {}
	public void testInit() {}

	public void disabledPeriodic() { Scheduler.getInstance().run(); }
	public void autonomousPeriodic() { Scheduler.getInstance().run(); }
	public void teleopPeriodic() { Scheduler.getInstance().run(); }
	public void testPeriodic() { Scheduler.getInstance().run(); }
}