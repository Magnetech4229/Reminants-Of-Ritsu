/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4229.robot;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import org.usfirst.frc.team4229.robot.ADXRS453Gyro;
//import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.*;
//import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	DifferentialDrive drive;
	Joystick left, right;
	Talon elevator;
	Talon DriveR;
	Talon DriveL;
	double speed;
	double topSpeed;
	double P;
	double I;
	double D;
	ADXRS453Gyro gyro;
	PIDController turner;

	public void turn(double speed){
		drive.tankDrive(speed, -1*speed);
		
	}

	
	
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		DriveR=new Talon(0);
		DriveL=new Talon(1);
		drive=new DifferentialDrive(DriveR, DriveL);
		left = new Joystick(0);
		right = new Joystick(1);
		elevator = new Talon(2);
		speed = 0;
		topSpeed = 0;
		gyro = new ADXRS453Gyro();
		gyro.startThread();
		gyro.calibrate();
		SmartDashboard.putNumber("P", 0.01);
		SmartDashboard.putNumber("I", 0);
		SmartDashboard.putNumber("D", 0);
		
		
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
			case kCustomAuto:
				// Put custom auto code here
				break;
			case kDefaultAuto:
			default:
				// Put default auto code here
				break;
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		drive.tankDrive(left.getRawAxis(1)*topSpeed, right.getRawAxis(1)*topSpeed);
		topSpeed = left.getZ()/2.0-0.5;
		SmartDashboard.putNumber("left", left.getZ() );
		speed = left.getZ();
		SmartDashboard.putNumber("gyro", gyro.getAngle());
		SmartDashboard.putNumber("GyroAngle", gyro.getAngle());
		SmartDashboard.putNumber("GyroPos", gyro.getPos());
		SmartDashboard.putNumber("GyroRate", gyro.getRate());
		SmartDashboard.putNumber("GyroTemp", gyro.getTemp());

		/* If right trigger pulls up
		 * if released stop the elevator
		 * if left trigger, elevator down
		 * if both triggers, ?
		 * if at top don't go up
		 * if at bottom don't go down
		 * 
		 * 
		 * 
		 * intakes
		 * left button 3 take intakes
		 * if released stop intaking
		 * right button 3 to shoot
		 * if released stop shooting
		 * 
		 * 
		 * 
		 */
		if(left.getRawButton(1) == false) {
			elevator.set(0);
		
		}
		else {
			elevator.set(speed);
			
			
		}
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	
	/**
	 * PID controller stuff keep it disabled
	 */
	
	@Override
	public void testInit(){
		P = SmartDashboard.getNumber("P", 0);
		I = SmartDashboard.getNumber("I", 0);
		D = SmartDashboard.getNumber("D", 0);
		
		SmartDashboard.putNumber("robotP", P);
		SmartDashboard.putNumber("robotI", I);
		SmartDashboard.putNumber("robotD", D);
		
		//(0.20, 0.000001, 0.3)
		turner = new PIDController(P, I, D, gyro, new gyroPIDoutput());
		gyro.reset();
		turner.setSetpoint(90);
		turner.enable();
		
		
		
	}
	
	
	
	
	
	@Override
	public void testPeriodic() {
		SmartDashboard.putNumber("GyroAngle", gyro.getAngle());
		SmartDashboard.putNumber("GyroPos", gyro.getPos());
		SmartDashboard.putNumber("GyroRate", gyro.getRate());
		SmartDashboard.putNumber("GyroTemp", gyro.getTemp());
		//SmartDashboard.putNumber("codep", P);
		
		
		
		
		
	}
	
	public class gyroPIDoutput implements PIDOutput {
		public void pidWrite(double output){
			turn(output);
			
		}
		
	}
}



