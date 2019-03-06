
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
    public static double desiredHeight;

    //MANIPULATOR
    public static enum WRISTSTATE {
        INACTIVE,
        MOVING,
    }

    public static WRISTSTATE wristState = WRISTSTATE.INACTIVE; // wrist state representation
    public static double targetAngle;

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
}
