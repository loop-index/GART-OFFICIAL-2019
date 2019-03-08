
package frc.robot.RobotLoop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Controls;
import frc.robot.RobotMap;
import frc.robot.Constants.ManipulatorConstants;
import frc.robot.RobotLoop.StateManager;
import frc.robot.RobotLoop.StateManager.CARGOSTATE;
import frc.robot.RobotLoop.StateManager.ELEVATORSTATE;
import frc.robot.RobotLoop.StateManager.MODE;
import frc.robot.RobotLoop.StateManager.WRISTSTATE;

/**
 * Essentially teleopPeriodic(), but written here for legibility
 */
public class TeleopLoop {

    public static void teleopLoop(){
        /* TEST CODE WITH GAMEPAD****************************
        // RobotMap.mManipulator.updateSensors();

        // //Change state on control
        // if (Controls.TEST.get()){
        //     StateManager.cargoState = CARGOSTATE.IN;
        // } else if (Controls.control_panel.getRawButton(2)){
        //     StateManager.cargoState = CARGOSTATE.OUT;
        // } else {
        //     StateManager.cargoState = CARGOSTATE.INACTIVE;
        // }

        // //Act according to state
        // if (StateManager.cargoState == CARGOSTATE.IN && !RobotMap.mManipulator.isCargoIn()) {
        //   RobotMap.mManipulator.cargoIntake();
        // } 
        // if (StateManager.cargoState == CARGOSTATE.IN && RobotMap.mManipulator.isCargoIn()){
        //     StateManager.cargoState = CARGOSTATE.INACTIVE;
        // }
        // if (StateManager.cargoState == CARGOSTATE.OUT) {
        //   RobotMap.mManipulator.cargoFire();
        // }
        // if (StateManager.cargoState == CARGOSTATE.INACTIVE) {
        //   RobotMap.mManipulator.stopCargoWheels();
        // }
    
        // if (Controls.control_panel.getRawButton(4)) {
        //   StateManager.targetAngle = 0;
        // } else if (Controls.control_panel.getRawButton(1)) {
        //   StateManager.targetAngle = ManipulatorConstants.lowerLimit - ManipulatorConstants.angleOffset + 5;
        // } else if (Controls.control_panel.getRawButton(10)) {
        //   // StateManager.targetAngle = ArmConstants.upperLimit - ArmConstants.angleOffset;
        //   StateManager.targetAngle = 25;
        // }
    
        // //HATCH
        // if (Controls.HATCH_TEST.uniquePress()) {
        //     RobotMap.mManipulator.hatchLocked = !RobotMap.mManipulator.hatchLocked;
        // }
        // RobotMap.mManipulator.lockHatch(RobotMap.mManipulator.hatchLocked);

        // RobotMap.mManipulator.setWristAngle(StateManager.targetAngle);
        // SmartDashboard.putNumber("target", StateManager.targetAngle);

        // //DRIVE
        // RobotMap.mDrivebase.driveByJoystick();
        ****************************/

        /*TEST CODE WITH CUSTOM CONTROL PANEL*/
        RobotMap.mManipulator.updateSensors();

        //Change state on control
        if (Controls.INTAKE.get()){
            StateManager.cargoState = CARGOSTATE.IN;
        } else if (Controls.FIRE.get()){
            StateManager.cargoState = CARGOSTATE.OUT;
        } else {
            StateManager.cargoState = CARGOSTATE.INACTIVE;
        }

        //Act according to state
        if (StateManager.cargoState == CARGOSTATE.IN && !RobotMap.mManipulator.isCargoIn()) {
          RobotMap.mManipulator.cargoIntake();
        } 
        if (StateManager.cargoState == CARGOSTATE.IN && RobotMap.mManipulator.isCargoIn()){
            StateManager.cargoState = CARGOSTATE.INACTIVE;
        }
        if (StateManager.cargoState == CARGOSTATE.OUT) {
          RobotMap.mManipulator.cargoFire();
        }
        if (StateManager.cargoState == CARGOSTATE.INACTIVE) {
          RobotMap.mManipulator.stopCargoWheels();
        }
    
        if (Controls.LEVEL_2.get()) {
          StateManager.targetAngle = 0;
        } else if (Controls.LEVEL_1.get()) {
          StateManager.targetAngle = ManipulatorConstants.lowerLimit - ManipulatorConstants.angleOffset + 5;
        } else if (Controls.LEVEL_3.get()) {
          StateManager.targetAngle = 22;
        }
    
        //HATCH
        if (Controls.HATCH_TEST.uniquePress()) {
            RobotMap.mManipulator.hatchLocked = !RobotMap.mManipulator.hatchLocked;
        }
        RobotMap.mManipulator.lockHatch(RobotMap.mManipulator.hatchLocked);

        RobotMap.mManipulator.setWristAngle(StateManager.targetAngle);
        SmartDashboard.putNumber("target", StateManager.targetAngle);

        //DRIVE
        RobotMap.mDrivebase.driveByJoystick();

        /************************MAIN CODE***************/
        /***********IN PROGRESS, DO NOT DELETE THIS*************/
        // RobotMap.mManipulator.updateSensors();

        // //CHANGE MODE
        // if (Controls.CARGO_MODE.uniquePress()) {
        //     StateManager.mode = MODE.CARGO;
        // } else if (Controls.HATCH_MODE.uniquePress()) {
        //     StateManager.mode = MODE.HATCH;
        // }

        // if (Controls.LEVEL_2.get()) {
        //     StateManager.targetAngle = 0;
        //   } else if (Controls.LEVEL_1.get()) {
        //     StateManager.targetAngle = ManipulatorConstants.lowerLimit - ManipulatorConstants.angleOffset + 5;
        //   } else if (Controls.LEVEL_3.get()) {
        //     StateManager.targetAngle = 22;
        //   }

    }

    //unused
    public TeleopLoop (){

        //-------------CONTROLS CHECK--------------//
        if (Controls.CARGO_GROUND.uniquePress()){
            StateManager.cargoState = CARGOSTATE.IN;
        }
        if (Controls.FIRE.get()) {
            StateManager.cargoState = CARGOSTATE.OUT;
        }
        if (Controls.TRACKING_MODE.uniquePress()){
            RobotMap.mVision.toggleMode();
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
                // RobotMap.mManipulator.setWristAngle(StateManager.targetAngle);
                
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
            // commented for implementing new methods
            case IN:
                RobotMap.mManipulator.cargoIntake();
                if (RobotMap.mManipulator.isCargoIn()){
                    StateManager.cargoState = CARGOSTATE.INACTIVE;
                }
                break;
            case OUT:
                RobotMap.mManipulator.cargoFire();
                if (Controls.FIRE.uniqueRelease()){
                    StateManager.cargoState = CARGOSTATE.INACTIVE;
                }
                break;
        }

        //Hatch Intake
        switch (StateManager.hatchState){
            case IN:
                RobotMap.mManipulator.lockHatch(true);
                break;
            case OUT:
                RobotMap.mManipulator.lockHatch(false);
                break;
        }

    }

    public void cargoFloorIntakeSetup(){
        if (Controls.floorIntakeTrigger()){
            StateManager.targetHeight = 0; //placeholder
            StateManager.targetAngle = 0; //placeholder

            StateManager.wristState = WRISTSTATE.MOVING;
            StateManager.elevatorState = ELEVATORSTATE.MOVING;
        }
    }
}
