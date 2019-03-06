package frc.robot.Constants;

public class ArmConstants {
    /*
        * ARM CONTAINS ARM AND INTAKE
    */
    //common constants
    public final static double g = 9.8; //in m/s, gravity acceleration
    public static double manipulatorWeight; //in kg
    public static double manipulatorLever; //in meter

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
    public static double angleOffset = 84.479; //arm at horizontal position
    public static double upperLimit = 119.165; //arm at fully upwards position
    public static double lowerLimit = 38.172; //arm at fully downwards position
    public static double rangeOfMovement = upperLimit - lowerLimit;
    public static double angleTolerance = 1;

    public static double CARGO_groundAngle = 38.172;

    //intake constants
    public static double maxIntakeOutput = 0.6;
    public static double maxFireOutput = -1;
    public static double maxManipulatorOutput = 0.8;

    //PID gains
    public static double kP_UP = 2.3*maxManipulatorOutput/rangeOfMovement;
    public static double kP_DOWN = 0.7*maxManipulatorOutput/rangeOfMovement;

}