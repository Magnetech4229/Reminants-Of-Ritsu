/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4229.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
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
	RobotDrive drive;
	Joystick left, right;
	Talon elevator;
	double speed;
	ADXRS453Gyro gyro;
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		drive=new RobotDrive(0,1);
		left = new Joystick(0);
		right = new Joystick(1);
		elevator = new Talon(2);
		speed = 0;
		gyro = new ADXRS453Gyro();
		gyro.startThread();
		gyro.calibrate();
		
		
		
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
		drive.tankDrive(left, right);
		SmartDashboard.putNumber("left", left.getZ() );
		speed = left.getZ();
		SmartDashboard.putNumber("gyro", gyro.getAngle());
		SmartDashboard.putNumber("GyroAngle", gyro.getAngle());
		SmartDashboard.putNumber("GyroPos", gyro.getPos());
		SmartDashboard.putNumber("GyroRate", gyro.getRate());
		SmartDashboard.putNumber("GyroTemp", gyro.getTemp());
		
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
	@Override
	public void testPeriodic() {
	}
}
