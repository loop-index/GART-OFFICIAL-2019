
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

    public static DRIVESTATE driveState; // drivebase state representation
    public static double desiredDistance;
    public static double desiredAngle;

    //ELEVATOR
    public static enum ELEVATORSTATE {
        INACTIVE, 
        MOVING,
    }

    public static ELEVATORSTATE elevatorState; // elevator state representation
    public static double desiredHeight;

    //MANIPULATOR
    public static enum WRISTSTATE {
        INACTIVE,
        MOVING,
    }

    public static WRISTSTATE wristState; // wrist state representation
    public static double desiredWristAngle;

    public static enum CARGOSTATE {
        INACTIVE,
        IN,
        OUT,
    }

    public static CARGOSTATE cargoState; // cargo state representation

    public static enum HATCHSTATE {
        IN,
        OUT,
    }

    public static HATCHSTATE hatchState; // hatch state representation

    //CLIMBER
    public static enum WHEELSTATE {
        INACTIVE,
        ENDGAME,
    }

    public static WHEELSTATE wheelState; // climb wheels state representation

    public static enum CYLINDERSTATE {
        INACTIVE, 
        ENDGAME,
    }

    public static CYLINDERSTATE cylinderState; // climb cylinders state representation

    //VISION
    public static enum VISIONSTATE {
        INACTIVE,
        ISTRACKING, 
        ARRIVED,
    }

    public static VISIONSTATE visionState; // vision state representation
}
