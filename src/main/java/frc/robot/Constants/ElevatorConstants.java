package frc.robot.Constants;

public class ElevatorConstants {
    //physical elevator constants
    public final static double kChainWheelDiameter = 2.5; //cm

    public static double kHighGearRatio = 22.67; //default
    public static double kLowGearRatio = 4.77; //activate when gearShifter is on

    public final static double kNeutralDeadband = 0.001;

    public final static double kDeadBand = 34.8; //cm

    //control constants
    public static double kCruiseVelocityRawUnits;
    public static double kAccelerationRawUnits;
    public final static double zeroingVelocity = -30; //cm/s
    public final static double zeroingOutput = -0.2;

    //physical arm constants
    //height
    //width

    public final static int kTimeoutMs = 30;
    public final static int kSlot_Distanc = 0;

    //PID gains slots 
    //	                                    			  kP   kI   kD   kF               Iz    PeakOut */
    public final static Gains kGains_Distanc = new Gains( 1, 0.0,  0.0, 0.0,            0,  0.80 );

    //for feedforward
    public static double kFirstStageMass; //kg
    public static double kSecondStageMass; //kg

    public static double kFirstStageMoment; //N.m
    public static double kSecondStageMoment; //N.m

    public static double kFirstStageStallVoltage_HighGear;
    public static double kSecondStageStallVoltage_HighGear;

    public static double kFirstStageStallVoltage_LowGear;
    public static double kSecondStageStallVoltage_LowGear;


    //sensor constants
    public static double kHeightTolerance = 2; //2 cm
    public static double kCountsPerRevSRX = 4096;
    public static double kEncGearRatio = 3; //output 3 - enc 1 

    //set heights, all in cm
    public final static double kHighestPosition = 185.0;
    public final static double kLowestPosition = kDeadBand;
    public final static double CARGO_level1Height = 69.862;
    public final static double CARGO_shipHeight = 90.000;
    public final static double CARGO_level2Height = 140.982;
    public final static double CARGO_level3Height = kHighestPosition;
    public final static double HATCH_level1Height = 48.272;
    public final static double HATCH_level2Height = 119.392;
    public final static double HATCH_level3Height = 0;

    public static double[][] setElevatorHeight = {{CARGO_level1Height, CARGO_level2Height, CARGO_level3Height}, //cargoHeight
                                         {HATCH_level1Height, HATCH_level2Height, HATCH_level3Height}}; //hatchHeight

    //elevator states
    public static String LOWEST_SWITCH_POSITION = "0011";
    public static String HIGHEST_SWITCH_POSITION = "1100";
}