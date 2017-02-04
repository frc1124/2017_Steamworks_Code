package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.RobotMap;
import org.usfirst.frc.team1124.robot.commands.Teleop;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;

/**
 * Heavily borrowed from FRC2465 Kauaibots.
 * https://github.com/Kauaibots/FRC/blob/master/Software/2016/DriveMule2016/src/org/usfirst/frc2465/Robot/subsystems/Drive.java#L182
 */
public class DriveTest extends PIDSubsystem {

	// sensors and actuators
    private CANTalon leftFrontSC, leftRearSC, rightFrontSC, rightRearSC;
    private RobotDrive robotDrive = null;
    private AHRS navx;

    // dimensions
    private static final double cWidth          = 22.0;                 // Distance between left/right wheels
    private static final double cLength         = 22.0;                 // Distance btwn front/back wheels
    private static final double wheelDiameter   = 4.0;                  // Per AndyMark Specs
    private static final double wheelRadius     = wheelDiameter / 2;
    public static final int ROTATE_DIRECTION  = -1;
    private static final double cRotK = ((cWidth + cLength)/2) / wheelRadius;               // Rotational Coefficient

    // inversion matrix for wheels
    private static double invMatrix[][] = new double[][] {
        {  -1, 1,  cRotK },
        {   1, 1, -cRotK },
        {   1, 1,  cRotK },
        {  -1, 1, -cRotK },        
    };

    // Control mode: Speed vs Voltage
    private CANTalon.ControlMode currControlMode;

    // Control mode variables
    public int maxOutputSpeed;
    public int maxTicksPer100MS;    
    public final int ticksPerRev = 4*1024;
    public final int num100msPerSec = 10;
    public final float motorRPMs = 2650.0f;
    public final float transRatio = 5.38461538461538f;
    private boolean fodEnable = true; // If true, in field-oriented drive mode. If false, in robot-centric mode.

    // PID values
    private double nextAutorotateValue = 0.0;
    private static final double autoRotateP = RobotMap.TURN_P;
    private static final double autoRotateI = RobotMap.TURN_I;
    private static final double autoRotateD = RobotMap.TURN_D;
    private static final double autoRotateOnTargetToleranceDegrees = 0.5;

    /**
     * Creates and initialize the drive object.
     * 
     */
	public DriveTest() {
		// Initialize with the PID values for the angle
		super("Drive",
                autoRotateP,
                autoRotateI,
                autoRotateD,
                0,
                0.02);

		// Create the wheel speed controllers
		this.leftFrontSC = new CANTalon(RobotMap.FRONT_LEFT);
		this.rightFrontSC = new CANTalon(RobotMap.FRONT_RIGHT);
		this.leftRearSC = new CANTalon(RobotMap.REAR_LEFT);
		this.rightRearSC = new CANTalon(RobotMap.REAR_RIGHT);

		// Create a robot drive
		this.robotDrive = new RobotDrive(this.leftFrontSC,this.leftRearSC,this.rightFrontSC,this.rightRearSC);

		// Create the navx
		this.navx = new AHRS(SPI.Port.kMXP);

        try {
        	// Set up the PID controller for the angle adjustment
            getPIDController().setContinuous( true );
            getPIDController().setInputRange(-180,180);
            getPIDController().setOutputRange(-1, 1);
            getPIDController().setAbsoluteTolerance(autoRotateOnTargetToleranceDegrees);
            setSetpoint(0.0);
            disable();
            
            robotDrive.setSafetyEnabled(false);

            // Calculate the maximum ticks per 100ms based on dimensions. This should be our max speed.
            maxTicksPer100MS = (int)((motorRPMs/transRatio)*ticksPerRev)/num100msPerSec;

            // Initialize the speed controllers for PID speed
            setMode( CANTalon.TalonControlMode.Speed);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}

	/**
	 * Initializes a motor based on the PID mode.
	 * 
	 * @param	motor	wheel to initialize based on current control mode
	 */
    private void initMotor( CANTalon motor, int wheel) {
        try {
            if ( currControlMode == CANTalon.TalonControlMode.Speed ) {
            	double p = 0.0;
            	double i = 0.0;
            	double d = 0.0;
            	switch (wheel) {
            	case RobotMap.FRONT_LEFT:
            		p = RobotMap.FRONT_LEFT_P;
            		i = RobotMap.FRONT_LEFT_I;
            		d = RobotMap.FRONT_LEFT_D;
            		break;
            	case RobotMap.FRONT_RIGHT:
            		p = RobotMap.FRONT_RIGHT_P;
            		i = RobotMap.FRONT_RIGHT_I;
            		d = RobotMap.FRONT_RIGHT_D;
            		break;
            	case RobotMap.REAR_LEFT:
            		p = RobotMap.REAR_LEFT_P;
            		i = RobotMap.REAR_LEFT_I;
            		d = RobotMap.REAR_LEFT_D;
            		break;
            	case RobotMap.REAR_RIGHT:
            		p = RobotMap.REAR_RIGHT_P;
            		i = RobotMap.REAR_RIGHT_I;
            		d = RobotMap.REAR_RIGHT_D;
            		break;
            	}
                motor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
				motor.setPID(p, i, d);
				motor.setF(5);
                motor.changeControlMode(CANTalon.TalonControlMode.Speed);
                motor.setCloseLoopRampRate(0);
            } else {
            	motor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
            }
            motor.enableBrakeMode(true);
            motor.setVoltageRampRate(0);
            motor.enableControl();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }    

    /**
     * Sets the PID mode for the wheels and initializes them.
     * 
     * @param	controlMode	control mode to set
     */
    public void setMode( CANTalon.TalonControlMode controlMode ) {
        
        currControlMode = controlMode;

        if ( currControlMode == CANTalon.TalonControlMode.Speed ) {
                maxOutputSpeed = maxTicksPer100MS;
        } else {
                maxOutputSpeed = 1;
        }
        
        initMotor(leftFrontSC, RobotMap.FRONT_LEFT);
        initMotor(rightFrontSC, RobotMap.FRONT_RIGHT);
        initMotor(leftRearSC, RobotMap.REAR_LEFT);
        initMotor(rightRearSC, RobotMap.REAR_RIGHT);    
    }    

    /**
     * Sets the default command.
     */
    public void initDefaultCommand() {
        setDefaultCommand(new Teleop());
    }

    /**
     * Determine if the motor is stalled. If so, reinit and print a message when it's back.
     * @param	motor	wheel to check
     * @param	strDescription	the output to send when it's clear
     */
    private void checkForRestartedMotor( CANTalon motor, String strDescription, int wheel ) {
        if ( currControlMode != CANTalon.TalonControlMode.Speed ) {  // kSpeed is the default
            try {
            	if ( !motor.isAlive() ) {
                    Timer.delay(0.10); // Wait 100 ms
                    initMotor( motor, wheel );
                    String error = "\n\n>>>>" + strDescription + "Talon Power Cycled - re-initializing";
                    System.out.println(error);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Adjusts the desired wheels speeds based on the velocities assuming the drive is moving sideways.
     * 
     * @param	velocities	current velocities
     * @param	wheelSpeeds	wheel speeds to adjust based on the respective velocity
     */
    private void mecanumDriveInvKinematics( double velocities[], double[] wheelSpeeds) {
        for ( int wheel = 0; wheel < 4; wheel ++ ) {
            wheelSpeeds[wheel] = 0;
            for ( int i = 0; i < 3; i++ ) {
                    wheelSpeeds[wheel] += velocities[i] * invMatrix[wheel][i];
            }
        }
    }    

    /**
     * Drive as omnidirectional with an X velocity, Y velocity, and rotational velocity.
     * 
     * @param	vX	desired translational X velocity
     * @param	vY	desired translational Y velocity
     * @param	vRot	desired rotational velocity
     */
    public void doMecanum( double vX, double vY, double vRot) {
        // If auto-rotating, replace vRot with the next
        // calculated value
        if ( getAutoRotation() ) {
            vRot = nextAutorotateValue;
        }
        
        boolean navxConnected = false;
        if ( navx != null ) { 
        	navxConnected = navx.isConnected();
        }
                
        // Field-oriented drive - Adjust input angle for gyro offset angle
        double currGyroAngleDegrees = 0;
        if ( fodEnable && navxConnected ) {
                currGyroAngleDegrees = navx.getYaw();
        }
        double currGyroAngleRadians = currGyroAngleDegrees * Math.PI/180;       
          
        double temp = vX * Math.cos( currGyroAngleRadians ) + vY * Math.sin( currGyroAngleRadians );
        vY = -vX * Math.sin( currGyroAngleRadians ) + vY * Math.cos( currGyroAngleRadians );
        vX = temp;
        
        try {
            double excessRatio = (double)1.0 / ( Math.abs(vX) + Math.abs(vY) + Math.abs(vRot) );
            if ( excessRatio < 1.0 ) {
                vX      *= excessRatio;
                vY      *= excessRatio;
                vRot    *= excessRatio;
            }
            
            vRot *= (1/cRotK);
            vRot *= ROTATE_DIRECTION;
            
            SmartDashboard.putNumber( "vRot", vRot);
            double wheelSpeeds[] = new double[4];
            double velocities[] = new double[3];
            velocities[0] = vX;
            velocities[1] = vY;
            velocities[2] = vRot;
            
            mecanumDriveInvKinematics( velocities, wheelSpeeds );
            
            checkForRestartedMotor( leftFrontSC, "Front Left", RobotMap.FRONT_LEFT );
            checkForRestartedMotor( rightFrontSC, "Front Right", RobotMap.FRONT_RIGHT );
            checkForRestartedMotor( leftRearSC, "Rear Left", RobotMap.REAR_LEFT );
            checkForRestartedMotor( rightRearSC, "Rear Right", RobotMap.REAR_RIGHT );
            
            leftFrontSC.set(maxOutputSpeed * wheelSpeeds[0] * -1 );
            rightFrontSC.set(maxOutputSpeed * wheelSpeeds[1]);
            leftRearSC.set(maxOutputSpeed * wheelSpeeds[2] * -1);
            rightRearSC.set(maxOutputSpeed * wheelSpeeds[3]);
			
            SmartDashboard.putNumber( "SpeedOut_FrontLeft", maxOutputSpeed * wheelSpeeds[0] * -1);
            SmartDashboard.putNumber( "SpeedOut_RearLeft", maxOutputSpeed * wheelSpeeds[2] * -1);
            SmartDashboard.putNumber( "SpeedOut_FrontRight", maxOutputSpeed * wheelSpeeds[1]);
            SmartDashboard.putNumber( "SpeedOut_RearRight", maxOutputSpeed * wheelSpeeds[3]);
            
            SmartDashboard.putNumber( "Speed_FrontLeft", leftFrontSC.getEncVelocity());
            SmartDashboard.putNumber( "Speed_RearLeft", leftRearSC.getEncVelocity());
            SmartDashboard.putNumber( "Speed_FrontRight", rightFrontSC.getEncVelocity());
            SmartDashboard.putNumber( "Speed_RearRight", rightRearSC.getEncVelocity());
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }        
    }

    /**
     * Sends the yaw back for angle adjustment.
     * 
     * @return	current yaw
     */
	@Override
	protected double returnPIDInput() {
        double currentYaw = 0.0;
        if ( navx.isConnected() ) {
            currentYaw = navx.getYaw();
        }
        SmartDashboard.putNumber( "AutoRotatePIDInput", currentYaw);
        return currentYaw;
    }

	/**
	 * Set up the angle for use.
	 * 
	 * @param	d	store the yaw for use in correcting when driving
	 */
	@Override
    protected void usePIDOutput(double d) {
        nextAutorotateValue = d;
        SmartDashboard.putNumber( "AutoRotatePIDOutput", nextAutorotateValue);
	}

	/**
	 * Turn on/off angle adjustment (i.e. angle PID)
	 * 
	 * @param	enable	enabled state of angle PID controller
	 */
    public void setAutoRotation(boolean enable) {
        if ( enable ) {
            getPIDController().enable();
        } else {
            getPIDController().disable();
        }
    }

    /**
     * Returns the state of angle adjustment enabled
     * 
     * @return	whether the angle adjustment is enabled
     */
    public boolean getAutoRotation() {
        SmartDashboard.putBoolean( "AutoRotateEnabled", getPIDController().isEnabled());
        return getPIDController().isEnabled();
    }

    /**
     * Sets state of field oriented drive
     * 
     * @param	enabled	set whether it should adjust for the field
     */
    public void setFODEnabled(boolean enabled) {
        fodEnable = enabled;
    }
    
    /**
     * Getter for field oriented drive mode
     * 
     * @return	state of field oriented drive
     */
    public boolean getFODEnabled() {
        return fodEnable;
    }

	public double getX() {
		return this.navx.getDisplacementX();
	}

	public double getY() {
		return this.navx.getDisplacementY();
	}

	public double getAngle() {
		return this.navx.getAngle();
	}

	public void resetGyro() {
		this.navx.reset();
		this.navx.resetDisplacement();
	}
}