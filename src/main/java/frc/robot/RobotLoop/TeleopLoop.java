
package frc.robot.RobotLoop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Controls;
import frc.robot.RobotMap;
import frc.robot.Constants.ManipulatorConstants;
import frc.robot.RobotLoop.StateManager;
import frc.robot.RobotLoop.StateManager.CARGOSTATE;
import frc.robot.RobotLoop.StateManager.ELEVATORSTATE;
import frc.robot.RobotLoop.StateManager.WRISTSTATE;

/**
 * Essentially teleopPeriodic(), but written here for legibility
 */
public class TeleopLoop {

    public static void teleopLoop(){
        RobotMap.mManipulator.updateSensors();

        //Change state on control
        if (Controls.TEST.get()){
            StateManager.cargoState = CARGOSTATE.IN;
        } 
        else if (Controls.joystick.getRawButton(2)){
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
    
        if (Controls.joystick.getRawButton(4)) {
          StateManager.targetAngle = 0;
        } else if (Controls.joystick.getRawButton(1)) {
          StateManager.targetAngle = ManipulatorConstants.lowerLimit - ManipulatorConstants.angleOffset + 10;
        } else if (Controls.joystick.getRawButton(10)) {
          // StateManager.targetAngle = ArmConstants.upperLimit - ArmConstants.angleOffset;
          StateManager.targetAngle = 35;
        }
    
        RobotMap.mClimber.joystickTest();
    
        RobotMap.mManipulator.setWristAngle(StateManager.targetAngle);
        SmartDashboard.putNumber("target", StateManager.targetAngle);
    }

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
                RobotMap.mManipulator.setWristAngle(StateManager.targetAngle);
                
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
            StateManager.targetAngle = 0; //placeholder

            StateManager.wristState = WRISTSTATE.MOVING;
            StateManager.elevatorState = ELEVATORSTATE.MOVING;
        }
    }
}
