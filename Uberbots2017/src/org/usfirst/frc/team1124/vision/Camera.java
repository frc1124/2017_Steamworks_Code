package org.usfirst.frc.team1124.vision;
import org.usfirst.frc.team1124.robot.RobotMap;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.cscore.CvSink;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.opencv.core.Mat;


public class Camera extends Subsystem {
	public static UsbCamera camera;
	public static CvSink cvSink;
		
	public Camera() {
		camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(RobotMap.CAMERA1_RES_X, RobotMap.CAMERA1_RES_Y);
		cvSink = CameraServer.getInstance().getVideo();
		CameraServer.getInstance().putVideo("visionCamera1",640,480);
	}
	public Mat getMat(){
		Mat source = new Mat();
		cvSink.grabFrame(source);
		return source;
	}
	protected void initDefaultCommand() {}
}
