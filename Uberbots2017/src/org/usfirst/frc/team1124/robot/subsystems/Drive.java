package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import utils.MiniPID;

public class Drive extends Subsystem {
	public MiniPID turnController = new MiniPID(0.1, 0, 0.1);
	public AHRS navx = new AHRS(SPI.Port.kMXP);
	public CANTalon frontRight = new CANTalon(3);
	public CANTalon frontLeft = new CANTalon(1);
	public CANTalon rearRight = new CANTalon(4);
	public CANTalon rearLeft = new CANTalon(2);
	public double lockAngle = 0.0;
	public RobotDrive driveTrain = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
	public int mode = 0; // 0:none, 1:arcade, 2:mec

	public Drive() {
		frontRight.setPID(1, 0, 1);
		frontRight.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		frontRight.setF(2);
		frontRight.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		frontRight.configEncoderCodesPerRev(4000);
		frontRight.setEncPosition(0);

		frontLeft.setPID(1, 0, 1);
		frontLeft.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		frontLeft.setF(2);
		frontLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		frontLeft.configEncoderCodesPerRev(4000);
		frontLeft.setEncPosition(0);

		rearRight.setPID(1, 0, 1);
		rearRight.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rearRight.setF(2);
		rearRight.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		rearRight.configEncoderCodesPerRev(4000);
		rearRight.setEncPosition(0);

		rearLeft.setPID(1, 0, 1);
		rearLeft.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rearLeft.setF(2);
		rearLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		rearLeft.configEncoderCodesPerRev(4000);
		rearLeft.setEncPosition(0);

	}

	public void initDefaultCommand() {
		this.setDefaultCommand(Robot.teleop);
	}

	public void lockAngle() {
		lockAngle = navx.getYaw();
	}

	public void run(double x, double y) {
		switch (mode) {
		case 0:
			System.out.println("not in mec or arcade mode");
			break;
		case 1:
			if (!frontLeft.getInverted() && !rearLeft.getInverted()) {
				frontLeft.setInverted(true);
				rearLeft.setInverted(true);
			}

			double correction = 0.0;
			double turn = x * y / Math.abs(y);
			double left = y - turn + correction;
			double right = y + turn + correction;

			frontLeft.set(left);
			rearLeft.set(left);
			frontRight.set(right);
			rearRight.set(right);
			break;
		case 2:
			if (frontLeft.getInverted() && rearLeft.getInverted()) {
				frontLeft.setInverted(true);
				rearLeft.setInverted(true);
			}

			double rotation = turnController.getOutput(navx.getYaw(), lockAngle);
			driveTrain.mecanumDrive_Cartesian(x, y, rotation, 0);
			break;
		default:
			System.out.println("This case litteraly cannot happen");
			break;
		}
	}

}