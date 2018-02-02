
package org.usfirst.frc.team4229.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're
 * inexperienced, don't. Unless you know what you are doing, complex code will
 * be much more difficult under this system. Use IterativeRobot or Command-Based
 * instead if you're new.
 */
@SuppressWarnings("deprecation")
public class Robot extends SampleRobot {
	RobotDrive myRobot = new RobotDrive(0, 1); // class that handles basic drive
												// operations
	Joystick leftStick = new Joystick(0); // set to ID 1 in DriverStation
	Joystick rightStick = new Joystick(1); // set to ID 2 in DriverStation
	Talon motor = new Talon(2); 
	public Robot() {
		myRobot.setExpiration(0.2);
	}
	/**
	 * Runs the motors autonomous
	 */
	public void autonomous() {
		for (int i = 0; i < 4; i++) {
			myRobot.drive(1,0.0);
			Timer.delay(2.0);
			myRobot.drive(1,1);
			Timer.delay(2.0);
			}
		myRobot.drive(0.0,0.0);
	}

	/**
	 * Runs the motors with tank steering.
	 */
	@Override
	public void operatorControl() {
		myRobot.setSafetyEnabled(true);
		while (isOperatorControl() && isEnabled()) {
			myRobot.tankDrive(leftStick, rightStick); 
			Timer.delay(0.005); // wait for a motor update time
		}
	}
	
	public void test(){
		
		while (isTest() && isEnabled()){
			motor.setSpeed(1.0);
		}
		}
				
	

}
