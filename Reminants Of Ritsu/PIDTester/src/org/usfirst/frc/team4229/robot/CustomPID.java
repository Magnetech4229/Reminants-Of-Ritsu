package org.usfirst.frc.team4229.robot;

public class CustomPID {
	
	public double p = 0; 
	public double i = 0; 
	public double d = 0;
	public double integral, previous_error, total_error, setpoint = 0;
	public double output, input;
	
	public void calculate(){
		double error = setpoint - input; // Error = Target - Actual
        this.integral += (error*.02); // Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
        double derivative = (error - previous_error) / .02;
        output = p*error + i*integral + d*derivative;
	}
}
