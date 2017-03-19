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
	public static String name = "Daedalus";
	public static double distPerTick = 10;
	public static int fps = 16;
	
	//sticks
	public static int firstDriverRightX = 4;
	public static int firstDriverRightY = 5;
	public static int firstDriverLeftX = 0;
	public static int firstDriverLeftY = 1;
	public static int secondDriverRightX = 4;
	public static int secondDriverRightY = 5;
	public static int secondDriverLeftX = 0;
	public static int secondDriverLeftY = 1;
	
	//buttons
	public static int firstDriverA = 1;
	public static int firstDriverB = 2;
	public static int firstDriverX = 3;
	public static int firstDriverY = 4;
	public static int secondDriverA = 1;
	public static int secondDriverB = 2;
	public static int secondDriverX = 3;
	public static int secondDriverY = 4;
	
	//CAN addresses
	public static int leftFront = 1;
	public static int leftBack = 2;
	public static int rightFront = 3;
	public static int rightBack = 4;
	public static int climber = 5;
	
	//analog pins
	public static int leftUltrasonic = 1;
	public static int rightUltrasonic = 0;
	
	//dio pins
	public static int visionSign = 0;
	public static int visionBitOne = 1;
	public static int visionBitTwo = 2;
	public static int visionBitThree = 3;
	public static int visionBitFour = 4;
	public static int visionBitFive = 5;
	public static int limit = 7;
	
	//pnumatics
	public static int[] gearDoor = {0,0,2}; 
	public static int[] ropeDoor = {0,1,3};
}