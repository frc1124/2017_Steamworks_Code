package org.usfirst.frc.team1124.robot;

<<<<<<< HEAD
import org.usfirst.frc.team1124.robot.commands.Auto;
import org.usfirst.frc.team1124.robot.commands.Teleop;
import org.usfirst.frc.team1124.robot.subsystems.Drive;
import org.usfirst.frc.team1124.robot.subsystems.Pneumatics;
=======
import org.usfirst.frc.team1124.robot.commands.TeleopDrive;
import org.usfirst.frc.team1124.robot.subsystems.Drive;
import org.usfirst.frc.team1124.vision.Camera;

>>>>>>> master
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {
<<<<<<< HEAD
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
=======

	// Operator Interface
	public static OI oi;

	// Subsystems
	public static Drive drive;
	public static Camera camera1;

	// Commands
	public static TeleopDrive teleopDrive;

	public void robotInit() {

		oi = new OI();

		drive = new Drive();
		camera1 = new Camera();

		teleopDrive = new TeleopDrive();

		// reseting the NavX
		drive.getNavx().reset();
		drive.getNavx().zeroYaw();
	}

	public void disabledInit() {}

	public void autonomousInit() {}

>>>>>>> master
	public void teleopInit() {}
	public void testInit() {}

	public void disabledPeriodic() { Scheduler.getInstance().run(); }
	public void autonomousPeriodic() { Scheduler.getInstance().run(); }
	public void teleopPeriodic() { Scheduler.getInstance().run(); }
	public void testPeriodic() { Scheduler.getInstance().run(); }
}