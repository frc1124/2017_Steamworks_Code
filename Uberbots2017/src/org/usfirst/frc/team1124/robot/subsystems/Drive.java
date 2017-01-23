package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.commands.TeleopDrive;
import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import utils.MiniPID;

public class Drive extends Subsystem {
	private double turnPoint;
	private MiniPID turn = new MiniPID(0.1,0,0);
	private AHRS navX;
	private CANTalon frontLeft = new CANTalon(1);
	private CANTalon rearLeft = new CANTalon(2);
	private CANTalon frontRight = new CANTalon(3);
	private CANTalon rearRight = new CANTalon(4);
	private RobotDrive drive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
	
	public Drive() { turn.setOutputLimits(1.0); }
	
	public double getTurnPoint() { return turnPoint; }
	public MiniPID getTurn() { return turn; }
	public RobotDrive getDrive() { return drive; }
	public AHRS getNavx() { return navX; }
	public void initDefaultCommand() { new TeleopDrive(); }
}
