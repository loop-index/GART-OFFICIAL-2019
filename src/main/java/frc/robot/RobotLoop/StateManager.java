
package frc.robot.RobotLoop;

/**
 * This class manages the states of all subsystems for use in periodic methods.
 */
public class StateManager {

    //DRIVEBASE
    public static enum DRIVESTATE {
        DRIVING,
        TURN_BY_GYRO,
        MOVE_BY_ENCODER,
    }

    public static DRIVESTATE driveState = DRIVESTATE.DRIVING; // drivebase state representation
    public static double desiredDistance;
    public static double desiredAngle;

    //ELEVATOR
    public static enum ELEVATORSTATE {
        INACTIVE, 
        MOVING,
    }

    public static ELEVATORSTATE elevatorState = ELEVATORSTATE.INACTIVE; // elevator state representation
    public static double targetHeight;
    public static int level;
    public static double lastHeight;
    public static boolean limitUp = false;
    public static boolean limitDown = false;

    public static enum MANUAL_MODE {
        INACTIVE,
        ELEVATOR,
        WRIST,
    }
    // public static MANUAL_MODE manualMode = MANUAL_MODE.ELEVATOR; //DEFAULT AUTO CONTROL
    public static boolean manualMode = true; //starts in manual mode
    public static enum CONTROL_MODE {
        MANUAL,
        AUTO, 
        WRIST_CALIBRATE,
        ELEVATOR_CALIBRATE,
    }
    public static CONTROL_MODE controlMode = CONTROL_MODE.MANUAL;


    //MANIPULATOR
    public static enum WRISTSTATE {
        INACTIVE,
        MOVING,
    }

    public static WRISTSTATE wristState = WRISTSTATE.INACTIVE; // wrist state representation
    public static double targetAngle;
    public static boolean changedFromMoveToHold = false;
    public static boolean calibrating = false;
    public static double lastAngle;

    public static enum CARGOSTATE {
        INACTIVE,
        IN,
        OUT,
    }

    public static CARGOSTATE cargoState = CARGOSTATE.INACTIVE; // cargo state representation

    public static enum HATCHSTATE {
        IN,
        OUT,
    }

    public static HATCHSTATE hatchState = HATCHSTATE.OUT; // hatch state representation
    public static boolean hatchLock = false;

    //CLIMBER
    public static enum WHEELSTATE {
        INACTIVE,
        ENDGAME,
    }

    public static WHEELSTATE wheelState = WHEELSTATE.INACTIVE; // climb wheels state representation

    public static enum CYLINDERSTATE {
        INACTIVE, 
        ENDGAME,
    }

    public static CYLINDERSTATE cylinderState = CYLINDERSTATE.INACTIVE; // climb cylinders state representation

    //VISION
    public static enum VISIONSTATE {
        INACTIVE,
        BALLTRACK,
        TARGETTRACK, 
        ARRIVED,
    }

    public static VISIONSTATE visionState = VISIONSTATE.INACTIVE; // vision state representation

    //CARGO/HATCH MODE
    public static enum MODE {
        CARGO,
        HATCH,
        WRIST_CALIBRATE,
    }
    public static MODE mode = MODE.CARGO; //INIT MODE IS CARGO
}
