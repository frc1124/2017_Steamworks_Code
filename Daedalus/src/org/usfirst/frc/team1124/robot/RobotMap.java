/*
___________                      ____ ____________    _____  
\__    ___/___ _____    _____   /_   /_   \_____  \  /  |  | 
  |    |_/ __ \\__  \  /     \   |   ||   |/  ____/ /   |  |_
  |    |\  ___/ / __ \|  Y Y  \  |   ||   /       \/    ^   /
  |____| \___  >____  /__|_|  /  |___||___\_______ \____   | 
             \/     \/      \/                    \/    |__| 
*/

package org.usfirst.frc.team1124.robot;

public class RobotMap {
	//global configuration
	public static final String name = "Daedalus";
	public static final double distPerTick = 10;
	public static final int fps = 16;
	
	//sticks
	public static final int firstDriverRightX = 4;
	public static final int firstDriverRightY = 5;
	public static final int firstDriverLeftX = 0;
	public static final int firstDriverLeftY = 1;
	public static final int secondDriverRightX = 4;
	public static final int secondDriverRightY = 5;
	public static final int secondDriverLeftX = 0;
	public static final int secondDriverLeftY = 1;

	//buttons
	public static final int firstDriverA = 1;
	public static final int firstDriverB = 2;
	public static final int firstDriverX = 3;
	public static final int firstDriverY = 4;
	public static final int secondDriverA = 1;
	public static final int secondDriverB = 2;
	public static final int secondDriverX = 3;
	public static final int secondDriverY = 4;
	
	//CAN addresses
	public static final int leftFront = 1;
	public static final int leftBack = 2;
	public static final int rightFront = 3;
	public static final int rightBack = 4;
	public static final int climber = 5;
	
	//analog pins
	public static final int leftUltrasonic = 1;
	public static final int rightUltrasonic = 0;
	
	//dio pins
	public static final int visionSign = 0;
	public static final int visionBitOne = 1;
	public static final int visionBitTwo = 2;
	public static final int visionBitThree = 3;
	public static final int visionBitFour = 4;
	public static final int visionBitFive = 5;
	public static final int limit = 7;
	
	//pnumatics
	public static final int[] gearDoor = {0,0,2}; 
	public static final int[] ropeDoor = {0,1,3};
}