
package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.Subsystems.*;

public class RobotMap {

    //VARIABLES INITIALIZATION

    //DRIVEBASE
    public static int CAN_LEFT_MASTER = 4, 
                        CAN_LEFT_FOLLOW = 3, 
                        CAN_RIGHT_MASTER = 2, 
                        CAN_RIGHT_FOLLOW = 1;
    public static SPI.Port NavXPort = SPI.Port.kMXP;

    //ELEVATOR
    public static int CAN_ELEVATOR_MASTER = 5, 
                        CAN_ELEVATOR_FOLLOW = 6;
    public static int BRAKE_SOLENOID = 0, 
                        GEARSHIFT_SOLENOID = 1;
    public static int STAGE1_UPPER_SWITCH = 0,
                        STAGE1_LOWER_SWITCH = 1,
                        STAGE2_UPPER_SWITCH = 2,
                        STAGE2_LOWER_SWITCH = 3;

    //MANIPULATOR
    public static int WRIST_VICTOR = 0, 
                        UPPER_INTAKE = 1, 
                        LOWER_INTAKE = 2;
    public static int ANALOG_ENCODER = 1;
    public static int HATCH_SOLENOID = 2;
    public static int CARGO_SWITCH = 4;

    //CLIMBER
    public static int LEFT_CLIMBER = 3, 
                        RIGHT_CLIMBER = 4;
    public static int PULLER = 2;
    //PNEUMATICS
    Compressor comp = new Compressor();

    //SUBSYSTEMS
    public static Drivebase mDrivebase = new Drivebase();
    public static Elevator mElevator = new Elevator();
    public static Manipulator mManipulator = new Manipulator();
    public static Climber mClimber = new Climber();
    public static Vision mVision = new Vision();

    public RobotMap(){ //initialization
        comp.setClosedLoopControl(true);
    }
}
