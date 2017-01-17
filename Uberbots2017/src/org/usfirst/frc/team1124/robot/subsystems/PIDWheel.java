package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class PIDWheel extends PIDSubsystem {

	private static final double WHEEL_D = 4;
	private static final double DEG_PER_PULSE = 1;

	private static final double DIS_PER_PULSE = (WHEEL_D * Math.PI) * (DEG_PER_PULSE / 360);

	private CANTalon motor;
	private Encoder encoder;

	public PIDWheel(int p, int i, int d, int CANchannel) {
		super(p, i, d);
		motor = new CANTalon(CANchannel);

		encoder = new Encoder(RobotMap.A_CHANNEL[CANchannel], RobotMap.B_CHANNEL[CANchannel], false, EncodingType.k4X);
		encoder.setDistancePerPulse(DIS_PER_PULSE);
	}

	@Override
	protected double returnPIDInput() {
		// returns distance the wheel rolled
		return encoder.getDistance();
	}

	@Override
	protected void usePIDOutput(double output) {
		// gives the output
		motor.pidWrite(output);
	}

	@Override
	protected void initDefaultCommand() {}

}
