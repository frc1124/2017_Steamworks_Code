package org.usfirst.frc.team1124.vision;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.cscore.CvSink;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.opencv.core.Mat;

	public class Camera extends Subsystem {
		public static UsbCamera camera;
		public static CvSink cvSink;
		
	public Camera(){
		
		camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(640, 480);
		cvSink = CameraServer.getInstance().getVideo();
		CameraServer.getInstance().putVideo("visionCamera1",640,480);
	
		
		
	}
	
	public Mat getMat(){
		Mat source = new Mat();
		cvSink.grabFrame(source);
		return source;
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
}
