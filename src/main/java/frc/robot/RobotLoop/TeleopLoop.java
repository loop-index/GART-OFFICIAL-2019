
package frc.robot.RobotLoop;

import frc.robot.Controls;
import frc.robot.RobotMap;
import frc.robot.RobotLoop.StateManager;
import frc.robot.RobotLoop.StateManager.ELEVATORSTATE;
import frc.robot.RobotLoop.StateManager.WRISTSTATE;

/**
 * Essentially teleopPeriodic(), but written here for legibility
 */
public class TeleopLoop {

    public TeleopLoop (){
        
        //DRIVEBASE
        switch (StateManager.driveState) {
            case DRIVING:
                RobotMap.mDrivebase.driveByJoystick();
                break;

            case MOVE_BY_ENCODER: //likely won't be called in teleop
            case TURN_BY_GYRO:  //as above
        }

        //MANIPULATOR

        switch (StateManager.wristState) {
            case INACTIVE:
                //stop method
                break;

            case MOVING:
                RobotMap.mManipulator.setWristAngle(StateManager.desiredWristAngle);
                
                if (RobotMap.mManipulator.wristOnTarget()){
                    StateManager.wristState = WRISTSTATE.INACTIVE;
                }
                break;
        }
    }

    public void cargoFloorIntakeSetup(){
        if (Controls.floorIntakeTrigger()){
            StateManager.desiredHeight = 0; //placeholder
            StateManager.desiredWristAngle = 0; //placeholder

            StateManager.wristState = WRISTSTATE.MOVING;
            StateManager.elevatorState = ELEVATORSTATE.MOVING;
        }
    }
}
