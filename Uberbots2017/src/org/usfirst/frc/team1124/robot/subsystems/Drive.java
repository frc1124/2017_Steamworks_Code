package org.usfirst.frc.team1124.robot.subsystems;

<<<<<<< HEAD
import org.usfirst.frc.team1124.robot.OI;
=======
import static org.usfirst.frc.team1124.robot.RobotMap.D;
import static org.usfirst.frc.team1124.robot.RobotMap.FRONT_LEFT;
import static org.usfirst.frc.team1124.robot.RobotMap.FRONT_RIGHT;
import static org.usfirst.frc.team1124.robot.RobotMap.I;
import static org.usfirst.frc.team1124.robot.RobotMap.INVERTED;
import static org.usfirst.frc.team1124.robot.RobotMap.P;
import static org.usfirst.frc.team1124.robot.RobotMap.REAR_LEFT;
import static org.usfirst.frc.team1124.robot.RobotMap.REAR_RIGHT;
import static org.usfirst.frc.team1124.robot.RobotMap.TRANS_PID;
import static org.usfirst.frc.team1124.robot.RobotMap.TURN_PID;

>>>>>>> master
import org.usfirst.frc.team1124.robot.Robot;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import utils.MiniPID;

public class Drive extends Subsystem {
<<<<<<< HEAD
	public MiniPID turnController = new MiniPID(0.06, 0, 0.1);
	public MiniPID forController = new MiniPID(0.035, 0, 0.1);
	public AHRS navx = new AHRS(SPI.Port.kMXP);
	public CANTalon frontRight = new CANTalon(3);
	public CANTalon frontLeft = new CANTalon(1);
	public CANTalon rearRight = new CANTalon(4);
	public CANTalon rearLeft = new CANTalon(2);
	public double lockAngle = 0.0;
	public RobotDrive driveTrain = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
	public int mode = 0; // 0:none, 1:arcade, 2:mec

	public Drive() {
		frontRight.setPID(2, 0, 1);
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

		rearRight.setPID(2, 0, 1);
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
		
		turnController.setOutputLimits(1);
		
		frontLeft.setInverted(true);
		rearLeft.setInverted(true);
=======

	// Set points for the turnController and transAngleController
	private double wantedYaw = 0;
	private double wantedDirection = 0;

	// PIDs for the rotation and translation of the robot
	private MiniPID yawController = new MiniPID(P[TURN_PID], I[TURN_PID], D[TURN_PID]);
	private MiniPID directionController = new MiniPID(P[TRANS_PID], I[TRANS_PID], D[TRANS_PID]);

	// The NavX
	private AHRS navX = new AHRS(SPI.Port.kMXP);

	private CANTalon[] wheels = new CANTalon[5];

	private RobotDrive drive;

	public Drive() {

		// Setting the output limits
		yawController.setOutputLimits(1.0);
		directionController.setOutputLimits(1.0);

		// Initiating all of the wheels
		for (int i = 1; i <= 4; i++) {
			wheels[i] = new CANTalon(i);
			wheels[i].setFeedbackDevice(FeedbackDevice.QuadEncoder);
			wheels[i].setPID(P[i], I[i], D[i]);
			wheels[i].setF(5);
			wheels[i].changeControlMode(CANTalon.TalonControlMode.PercentVbus);
			wheels[i].configEncoderCodesPerRev(256);
			wheels[i].configMaxOutputVoltage(24);
			wheels[i].setInverted(INVERTED[i]);
		}

		// Initiating the actual drive train
		drive = new RobotDrive(wheels[FRONT_LEFT], wheels[REAR_LEFT], wheels[FRONT_RIGHT], wheels[REAR_RIGHT]);
	}

	public void initDefaultCommand() {
		this.setDefaultCommand(Robot.teleopDrive);
	}

	public CANTalon getFrontLeft() {
		return wheels[FRONT_LEFT];
	}

	public CANTalon getFrontRight() {
		return wheels[FRONT_RIGHT];
	}

	public CANTalon getRearLeft() {
		return wheels[REAR_LEFT];
	}
>>>>>>> master

	}

<<<<<<< HEAD
	public void initDefaultCommand() {
		this.setDefaultCommand(Robot.teleop);
=======
	public MiniPID getYawController() {
		return yawController;
	}

	public RobotDrive getDrive() {
		return drive;
>>>>>>> master
	}

	public void lockAngle() {
		lockAngle = navx.getYaw();
	}

<<<<<<< HEAD
	public void run(double x, double y) {
		switch (mode) {
		case 0:
			System.out.println("not in mec or arcade mode");
			break;
		case 1:
			double correction = 0;
			double turn = x * y / Math.abs(y);
			double left = y + turn + correction;
			double right = y - turn + correction;

			frontLeft.set(left);
			rearLeft.set(left);
			frontRight.set(right);
			rearRight.set(right);
			break;
		case 2:
			NetworkTable.getTable("encoders").putNumber("rearRight", Robot.drive.rearRight.getEncPosition());
			//if(x <= -0.1) { y += 0.3; }
			//if(x >= 0.1) { y -= 0.3; }
			double rotation = turnController.getOutput(navx.getYaw(), lockAngle);
			driveTrain.mecanumDrive_Cartesian(x, y, rotation, 0);
			break;
		default:
			System.out.println("This case litteraly cannot happen");
			break;
		}
	}

}
=======
	public double getWantedYaw() {
		return wantedYaw;
	}

	public void setWantedYaw(double yaw) {
		this.wantedYaw = yaw;
	}

	public MiniPID getDirectionController() {
		return directionController;
	}

	public double getWantedDirection() {
		return wantedDirection;
	}

	public void setWantedDirection(double direction) {
		this.wantedDirection = direction;
	}

}
>>>>>>> master
