package org.usfirst.frc.team1124.robot.utils;

import org.usfirst.frc.team1124.robot.RobotMap;
import edu.wpi.first.wpilibj.DigitalInput;

public class VisionComms {
	private DigitalInput sign = new DigitalInput(RobotMap.visionSign);
	private DigitalInput bitOne = new DigitalInput(RobotMap.visionBitOne);
	private DigitalInput bitTwo = new DigitalInput(RobotMap.visionBitTwo);
	private DigitalInput bitThree = new DigitalInput(RobotMap.visionBitThree);
	private DigitalInput bitFour = new DigitalInput(RobotMap.visionBitFour);
	private DigitalInput bitFive = new DigitalInput(RobotMap.visionBitFive);
	private double scaleFactor = 40.0;
	
	public double read() {
		double val = 0.0;
		val += (bitOne.get()) ? 1 : 0;
		val += (bitTwo.get()) ? 2 : 0;
		val += (bitThree.get()) ? 4 : 0;
		val += (bitFour.get()) ? 8 : 0;
		val += (bitFive.get()) ? 16 : 0;
		return((sign.get()) ? -val/scaleFactor : val/scaleFactor);
	}
	//I will make this a single line... one day...
}
