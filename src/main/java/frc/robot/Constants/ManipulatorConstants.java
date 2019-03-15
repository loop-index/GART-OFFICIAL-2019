package frc.robot.Constants;

public class ManipulatorConstants {
    /*
        * MANIPULATOR CONTAINS ARM AND INTAKE
    */
    //COMMON CONSTANTS
    public final static double g = 9.8; //in m/s, gravity acceleration
    public static double manipulatorWeight = 6; //in kg
    public static double manipulatorLever = 0.24; //in meter
    public static double motorGearboxRatio = 100/1;
    public static double chainWheelRatio = 48/18;
    public static double totalRatio = motorGearboxRatio * chainWheelRatio;

    //arm constants
    //physical arm constants

    //set motor outputs
    public static double normalMotorOutput;

    //raw analog values of ma3 encoder
    public static double ma3MinValue = 11.0;
    public static double ma3MaxValue = 4038.0;

    //SET ANGLES ----- all in DEGREES
    /*
        * arm moves up  : angle +
        * arm moves down: angle -
    */
    public static double angleOffset = 126.5; //arm at horizontal position
    public static double upperLimit = angleOffset + 94; //arm at fully upwards position
    public static double lowerLimit = angleOffset - 42; //arm at fully downwards position
    public static double rangeOfMovement = upperLimit - lowerLimit;
    public static double angleTolerance = 10;

    //intake angles
    public static double CARGO_groundIntakeAngle = -42;
    public static double HATCH_intakeAngle;

    //fire angles
    public static double REST_ANGLE = upperLimit - angleOffset;
    public static double CARGO_LV1Angle = 0;
    public static double CARGO_LV2Angle = 0;
    public static double CARGO_LV3Angle = 32.7;  
    public static double CARGO_shipAngle = 0;
    public static double HATCH_LV1Angle;
    public static double HATCH_LV2Angle;
    public static double HATCH_LV3Angle;

    public static double[][] setWristAngles = {
        {CARGO_LV1Angle, CARGO_LV2Angle, CARGO_LV3Angle},
        {HATCH_LV1Angle, HATCH_LV2Angle, HATCH_LV3Angle}
    };

    //INTAKE CONSTANTS
    public static double maxIntakeOutput = -0.8;
    public static double maxFireOutput = 1;
    public static double maxManipulatorOutput = 1;
    public static double calibrateOutput = -0.2;

    //unused
    public static double cargoFireTimeout = 1; //sec
    public static double cargoIntakeTimeout = 1; //sec

    //PID GAINS
    //if the error is less than smallError we need to increase gains for better correction
    //However, if the gain is too high, the mechanism will oscillates
    //Therefore, if (error < smallError && error > epsilon), increase PID gains
    public static double smallError = 7;
    public static double epsilon = 1;

    public static double kP_UP = 4*maxManipulatorOutput/rangeOfMovement;
    public static double kI_UP = 0.03;
    public static double kP_DOWN = maxManipulatorOutput/rangeOfMovement;

    public static double manualControlTolerance = 5;
}