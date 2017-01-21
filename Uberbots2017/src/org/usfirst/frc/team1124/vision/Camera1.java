package org.usfirst.frc.team1124.vision;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;

import org.opencv.core.Mat;

	public class Camera1 {
		public static UsbCamera camera;
		public static CvSink cvSink;
		
	public Camera1(){
		
		camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(640, 480);
		cvSink = CameraServer.getInstance().getVideo();
		CvSource outputstream = CameraServer.getInstance().putVideo("visionCamera1",640,480);
	
	}
}
