package org.usfirst.frc.team1124.robot;

import java.nio.ByteBuffer;
import java.util.Calendar;

import org.usfirst.frc.team1124.robot.commands.AutoQueue;
import org.usfirst.frc.team1124.robot.commands.DriveForward;
import org.usfirst.frc.team1124.robot.commands.Teleop;
import org.usfirst.frc.team1124.robot.commands.TestGearDoor;
import org.usfirst.frc.team1124.robot.commands.autonomous.*;
import org.usfirst.frc.team1124.robot.subsystems.Climber;
import org.usfirst.frc.team1124.robot.subsystems.Drive;
import org.usfirst.frc.team1124.robot.subsystems.GearDoor;
import org.usfirst.frc.team1124.vision.Camera;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Robot extends IterativeRobot {
	public static Drive drive;
	public static GearDoor gearDoor;
	public static Climber climber;
	public static OI oi;
	public static long lastTime;
	public static Command teleop;
	public static AutoQueue auto;
	public static Camera camera;
	public static Command dumbAuto;
	public static Command noAuto;
	public static AnalogInput gearDoorDetect;
	public static I2C arduinoConnection;
	public static int modeFlag = 0;
	//autos
	public static Command placeGearOnCenter;
	public static Command placeGearOnCenterAndLeft;
	public static Command placeGearOnCenterAndRight;
	public static Command placeGearOnLeft;
	public static Command placeGearOnRight;
	private static double velocityX = 0;
	private static double velocityY = 0;
	private static double velocityZ = 0;

	public void robotInit() {
		drive = new Drive();
		gearDoor = new GearDoor();
		gearDoor.setDefaultCommand(new TestGearDoor());
		climber = new Climber();
		teleop = new Teleop();
		camera = new Camera();

		auto = new AutoQueue();
		drive.navx.reset();
		drive.navx.resetDisplacement();
		dumbAuto = new DriveForward(96);
		noAuto = new DriveForward(0);
		RobotMap.init();
		arduinoConnection = new I2C(I2C.Port.kOnboard, 0);

		oi = new OI();
		lastTime = Calendar.getInstance().getTimeInMillis();
		
		//autos
		placeGearOnCenter = new PlaceGearOnCenter();
		placeGearOnCenterAndLeft = new PlaceGearOnCenterAndLeft();
		placeGearOnCenterAndRight = new PlaceGearOnCenterAndRight();
		placeGearOnRight = new PlaceGearOnRight();
		placeGearOnLeft = new PlaceGearOnLeft();
		
	}

	public void disabledInit() {}
	@SuppressWarnings("deprecation")
	public void autonomousInit() {
		
		int autoMode = ((int)NetworkTable.getTable("dash").getNumber("auto"));
		switch(autoMode) {
		case 0: noAuto.start(); break;
		case 1: dumbAuto.start(); break;
		case 2: placeGearOnCenter.start(); break;
		case 3: placeGearOnCenterAndLeft.start(); break;
		case 4: placeGearOnCenterAndRight.start(); break;
		case 5: placeGearOnRight.start(); break;
		case 6: placeGearOnLeft.start(); break;
		default: noAuto.start(); break;
		}
	}
	public void teleopInit() {}
	public void testInit() {}

	public static double speedX(){
		return velocityX;
	}
	
	public static double speedY(){
		return velocityY;
	}
	
	public static double speedZ(){
		return velocityZ;
	}
	
	public void disabledPeriodic() { Scheduler.getInstance().run(); updateArduino(); }
	public void autonomousPeriodic() { Scheduler.getInstance().run();updateArduino(); }
	public void teleopPeriodic() { 
		updateDashboard();
		Scheduler.getInstance().run();
		updateArduino();
	}
	public void testPeriodic() { Scheduler.getInstance().run(); updateArduino(); }
	
	public void updateArduino(){
		boolean allienceIsRed = (DriverStation.getInstance().getAlliance().equals(DriverStation.Alliance.Red));
		int teleopD = 1, teleopE = 2, autoD = 3, autoE = 4, eStop = 5;
		int disconnected  = 6, searchFeeder = 7, gearReady = 8, gearIn = 9, searchLift = 10, climb = 11;
		int mode = disconnected;
		boolean hasGear = gearDoorDetect.getVoltage() != 0;
		if(!DriverStation.getInstance().isSysActive()){
			mode = eStop;
		}else{
			if(modeFlag > 0){
				mode = modeFlag;
			}else{
				if(isEnabled()){
					if(isOperatorControl()){
						mode = teleopE;
					}
					else if(isAutonomous()){
						mode = autoE;
					}
				}else{
					if(isOperatorControl()){
						mode = teleopD;
					}
					else if(isAutonomous()){
						mode = autoD;
					}
				}
			}
		}
		
		ByteBuffer data =  ByteBuffer.allocate(4);
		data.put(0, (byte) 0xFF);
		data.put(1, (byte)((allienceIsRed) ? 1:0));
		data.put(2, (byte)(mode));
		data.put(3, (byte)((hasGear) ? 1:0));
		arduinoConnection.writeBulk(data , 3);
	}
	
	public void updateDashboard() {
//		long curTime = Calendar.getInstance().getTimeInMillis();
//		long diff = curTime - lastTime;
//		lastTime = curTime;
//		double speed = Math.PI * 4;
		
		velocityX -= drive.distancer.getAccelerometerX();
		velocityY += drive.distancer.getAccelerometerY();
		velocityZ += drive.distancer.getAccelerometerZ();
		
		NetworkTable.getTable("dash").putBoolean("limit", climber.limit.getAverageVoltage()>2);
		double speed = (( Math.abs(drive.frontLeft.getSpeed()) + Math.abs(drive.frontRight.getSpeed() + Math.abs(drive.rearRight.getSpeed()) + Math.abs(drive.rearLeft.getSpeed())) )/4) * (4*Math.PI) / 60;
		NetworkTable.getTable("dash").putNumber("speed", speed);
//		NetworkTable.getTable("dash").putNumber("us1", drive.ultrasonic1MM());
//		NetworkTable.getTable("dash").putNumber("us2", drive.ultrasonic2MM());
		NetworkTable.getTable("dash").putNumber("Accelerometer X", drive.distancer.getAccelerometerX());
		NetworkTable.getTable("dash").putNumber("Accelerometer Y", drive.distancer.getAccelerometerY());
		NetworkTable.getTable("dash").putNumber("Accelerometer Z", drive.distancer.getAccelerometerZ());
		NetworkTable.getTable("dash").putNumber("gearDetect", gearDoor.detect());
	}
}