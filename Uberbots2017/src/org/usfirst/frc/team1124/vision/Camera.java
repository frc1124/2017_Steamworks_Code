package org.usfirst.frc.team1124.vision;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.cscore.CvSink;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.opencv.core.Mat;

public class Camera extends Subsystem {
	public static UsbCamera camera;
	public static CvSink cvSink;

	public static final int CAMERA_EXPOSURE = 80;

	public static final int CAMERA1_RES_X = 640;
	public static final int CAMERA1_RES_Y = 480;

	public Camera() {
		camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(CAMERA1_RES_X, CAMERA1_RES_Y);
		camera.setExposureManual(CAMERA_EXPOSURE);
		cvSink = CameraServer.getInstance().getVideo();
		CameraServer.getInstance().putVideo("visionCamera1", 640, 480);
	}

	public Mat getMat() {
		Mat source = new Mat();
		cvSink.grabFrame(source);
		return source;
	}

	protected void initDefaultCommand() {}
}
