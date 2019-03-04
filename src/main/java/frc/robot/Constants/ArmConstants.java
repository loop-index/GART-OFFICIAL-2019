package frc.robot.Constants;

public class ArmConstants {
    /*
        * ARM CONTAINS ARM AND INTAKE
    */
    //common constants
    public static double mass;

    //arm constants
    //physical arm constants

    //set motor outputs
    public static double normalMotorOutput;

    //raw analog values of ma3 encoder
    public static double ma3MinValue = 11.0;
    public static double ma3MaxValue = 4038.0;

    //set angles ----- all in DEGREES
    /*
        * arm moves up  : angle +
        * arm moves down: angle -
    */
    public static double angleOffset = 133.11; //arm at horizontal position
    public static double upperLimit; //arm at fully upwards position
    public static double lowerLimit; //arm at fully downwards position
    public static double rangeOfMovement = upperLimit - lowerLimit;
    public static double angleTolerance = 1;

    //PID gains

    //intake constants
    public static double intakeOutput = 0;
    public static double fireOutput = 1;
}