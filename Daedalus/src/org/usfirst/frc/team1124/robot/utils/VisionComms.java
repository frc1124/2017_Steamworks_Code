package org.usfirst.frc.team1124.robot.utils;

import org.usfirst.frc.team1124.robot.RobotMap;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionComms {
	private static DigitalInput sign = new DigitalInput(RobotMap.visionSign);
	private static DigitalInput bitOne = new DigitalInput(RobotMap.visionBitOne);
	private static DigitalInput bitTwo = new DigitalInput(RobotMap.visionBitTwo);
	private static DigitalInput bitThree = new DigitalInput(RobotMap.visionBitThree);
	private static DigitalInput bitFour = new DigitalInput(RobotMap.visionBitFour);
	private static DigitalInput bitFive = new DigitalInput(RobotMap.visionBitFive);
	private static double scaleFactor = 40.0;
	
	public static double read() {
		double val = 0.0;
		val += (bitOne.get()) ? 1 : 0;
		val += (bitTwo.get()) ? 2 : 0;
		val += (bitThree.get()) ? 4 : 0;
		val += (bitFour.get()) ? 8 : 0;
		val += (bitFive.get()) ? 16 : 0;
		
		NetworkTable.getTable("vis").putBoolean("sign", bitOne.get());
		
		return((sign.get()) ? -val/scaleFactor : val/scaleFactor);
	}
	//I will make this a single line... one day...
	//maybe a reallllllly long string of ternaries
}
