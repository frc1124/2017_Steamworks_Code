package org.usfirst.frc.team1124.robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class Ultrasonic{
	
	private static AnalogInput in = new AnalogInput(0);
	
	//@param factor The factor of the value (ex. for nanos, factor is one. For millis, factor is one million)
	//@return Returns the change in distance over the change in nano seconds
	public static double getValue(int factor){
		long dt1 = System.nanoTime();
		long deltat = 0;
		
		double dv1 = in.getVoltage();
		double deltav = 0;
		
		double value = 0;
		
		for(int i = 0; i < 2; i++){
			if(deltat != 0){
				value = deltav / (deltat / factor);
			}
			deltat = System.nanoTime() - dt1;
			deltav = in.getVoltage() - dv1;
		}
		return value;
	}
}
