
package frc.robot.Constants;

import java.util.Arrays;

public class Utils {

    public static double getMedian(double... a){
		Arrays.sort(a);
		return a[(a.length-1)/2];
	}

	public static double cmToSteps(double cm, double gearRatio, double ppr, double wheelRadius) {
		return cm*(gearRatio*ppr)/(wheelRadius*2*Math.PI);
	}

	public static double stepsToCm(double steps, double gearRatio, double ppr, double wheelRadius) {
		return steps*(wheelRadius*2*Math.PI)/(gearRatio*ppr);
	}
	
	public static double d2r(double degs) {
		return degs*Math.PI/180;
	}
	
	public static double r2d(double rads) {
		return rads*180/Math.PI;
	}
	
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
