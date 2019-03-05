
package frc.robot.RobotLoop;

import frc.robot.Controls;
import frc.robot.RobotMap;
import frc.robot.RobotLoop.StateManager;
import frc.robot.RobotLoop.StateManager.CARGOSTATE;
import frc.robot.RobotLoop.StateManager.ELEVATORSTATE;
import frc.robot.RobotLoop.StateManager.WRISTSTATE;

/**
 * Essentially teleopPeriodic(), but written here for legibility
 */
public class TeleopLoop {

    public TeleopLoop (){
        //-------------CONTROLS CHECK--------------//
        if (Controls.CARGO_GROUND.uniquePress()){
            StateManager.cargoState = CARGOSTATE.IN;
        }
        if (Controls.FIRE.get()) {
            StateManager.cargoState = CARGOSTATE.OUT;
        }
        
        //---------------DRIVEBASE-----------------//
        switch (StateManager.driveState) {
            case DRIVING:
                RobotMap.mDrivebase.driveByJoystick();
                break;

            case MOVE_BY_ENCODER: //likely won't be called in teleop
            case TURN_BY_GYRO:  //as above
        }

        //--------------MANIPULATOR----------------//
        //Wrist
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

        //Cargo Intake
        switch (StateManager.cargoState)  {
            case INACTIVE:
                RobotMap.mManipulator.stopCargoWheels();
                break;
            case IN:
                RobotMap.mManipulator.cargoIntake(true);
                if (RobotMap.mManipulator.isCargoIn()){
                    StateManager.cargoState = CARGOSTATE.INACTIVE;
                }
                break;
            case OUT:
                RobotMap.mManipulator.cargoIntake(false);
                if (Controls.FIRE.uniqueRelease()){
                    StateManager.cargoState = CARGOSTATE.INACTIVE;
                }
                break;
        }

        //Hatch Intake
        switch (StateManager.hatchState){
            case IN:
                RobotMap.mManipulator.hatchIntake(true);
                break;
            case OUT:
                RobotMap.mManipulator.hatchIntake(false);
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
