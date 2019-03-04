
package frc.robot.Constants;

import java.util.Arrays;

public class Utils {

    public static double getMedian(double... a){
		Arrays.sort(a);
		return a[(a.length-1)/2];
	}
}
