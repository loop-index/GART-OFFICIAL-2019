
package frc.robot.Constants;

import java.util.Arrays;

public class Utils {

	/**
	 * Get the median of inputs
	 * @param a inputs
	 * @return median of inputs
	 */
    public static double getMedian(double... a){
		Arrays.sort(a);
		return a[(a.length-1)/2];
	}

	/**
	 * convert centimeters to steps
	 */
	public static double cmToSteps(double cm, double gearRatio, double ppr, double wheelRadius) {
		return cm*(gearRatio*ppr)/(wheelRadius*2*Math.PI);
	}

	/**
	 * convert encoder steps to centimeters
	 * @param steps number of encoder steps
	 * @param gearRatio ratio input : output (?)
	 * @param ppr pulse per revolution
	 * @param wheelRadius wheel radius in centimeters
	 * @return calculated results
	 */
	public static double stepsToCm(double steps, double gearRatio, double ppr, double wheelRadius) {
		return steps*(wheelRadius*2*Math.PI)/(gearRatio*ppr);
	}
	
	/**
	 * Convert degrees to radians
	 * @param degs degrees
	 * @return radians
	 */
	public static double d2r(double degs) {
		return degs*Math.PI/180;
	}
	
	/**
	 * Convert radians to degrees
	 * @param rads radians
	 * @return degrees
	 */
	public static double r2d(double rads) {
		return rads*180/Math.PI;
	}
	
	/**
	 * Keep remainder positive
	 * @param x Divider
	 * @param y Divisor
	 * @return positive remainder
	 */
	public static double mod(double x, double y) {
		double result = x % y;
		while (result < 0) {
			result += y;
		}
		return result;
	}
	
	//bound to [0,360) deg
	public static double boundAngle(double angle) {
		return mod(angle,360);
	}
	
	/**
	 * bound values to -180 to 180
	 * @param angle_degrees angle
	 * @return modified angle
	 */
	public static double boundHalfDegrees(double angle_degrees) {
		double result = angle_degrees;
        while (result >= 180.0) result -= 360.0;
        while (result < -180.0) result += 360.0;
        return result;
    }
	
	public static double limitNumber(double rawNumber, double lowerLimit, double upperLimit) {
		if (rawNumber < lowerLimit) {
			return lowerLimit;
		} else if (rawNumber > upperLimit) {
			return upperLimit;
		} else {
			return rawNumber;
		}
	}
	
	public static boolean aeq (double number1, double number2, double epsilon) { // approx. equals
		return Math.abs(number1 - number2) <= epsilon;
	}

	public static double countsToRad(double counts, double cpr) {
		return counts*2*Math.PI/cpr;
	}

	public static double radToCounts(double rad, double cpr) {
		return cpr*rad/2/Math.PI;
	}

	public static double deadband(double input, double deadband) {
		return (aeq(input, 0, deadband)) ? 0 : input;
	}
}
