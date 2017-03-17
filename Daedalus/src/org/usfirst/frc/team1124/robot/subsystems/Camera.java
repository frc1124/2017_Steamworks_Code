package org.usfirst.frc.team1124.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.cscore.CvSink;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Camera extends Subsystem {
	public static UsbCamera camera;
	public static CvSink cvSink;

	public static final int CAMERA_RES_X = 640;
	public static final int CAMERA_RES_Y = 480;

	public Camera() {
		camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(CAMERA_RES_X, CAMERA_RES_Y);
		cvSink = CameraServer.getInstance().getVideo();
		CameraServer.getInstance().putVideo("visionCamera", CAMERA_RES_X, CAMERA_RES_Y);
		camera.setFPS(15);
	}
	
	protected void initDefaultCommand() {}
}