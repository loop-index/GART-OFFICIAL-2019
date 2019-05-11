
package frc.robot.RobotLoop;

import java.beans.Statement;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.CSVLogger;
import frc.robot.Controls;
import frc.robot.RobotMap;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.ManipulatorConstants;
import frc.robot.Constants.Utils;
import frc.robot.RobotLoop.StateManager;
import frc.robot.RobotLoop.StateManager.CARGOSTATE;
import frc.robot.RobotLoop.StateManager.CONTROL_MODE;
import frc.robot.RobotLoop.StateManager.ELEVATORSTATE;
import frc.robot.RobotLoop.StateManager.MANUAL_MODE;
import frc.robot.RobotLoop.StateManager.MODE;
import frc.robot.RobotLoop.StateManager.WRISTSTATE;
import frc.robot.Subsystems.Elevator;

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
    
        if (Controls.control_panel.getRawButton(4)) {
          StateManager.targetAngle = 0;
        } else if (Controls.control_panel.getRawButton(1)) {
          StateManager.targetAngle = ManipulatorConstants.lowerLimit - ManipulatorConstants.angleOffset + 5;
        } else if (Controls.control_panel.getRawButton(10)) {
          // StateManager.targetAngle = ArmConstants.upperLimit - ArmConstants.angleOffset;
          StateManager.targetAngle = 25;
        }
    
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
        // RobotMap.mManipulator.updateSensors();

        // //Change state on control
        // if (Controls.INTAKE.get()){
        //     StateManager.cargoState = CARGOSTATE.IN;
        // } else if (Controls.FIRE.get()){
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
    
        // if (Controls.LEVEL_2.get()) {
        //   StateManager.targetAngle = 0;
        // 
    // } else if (Controls.LEVEL_1.get()) {
        //   StateManager.targetAngle = ManipulatorConstants.lowerLimit - ManipulatorConstants.angleOffset + 5;
        // } else if (Controls.LEVEL_3.get()) {
        //   StateManager.targetAngle = 22;
        // }
    
        // //HATCH
        // if (Controls.HATCH_TEST.uniquePress()) {
        //     RobotMap.mManipulator.hatchLocked = !RobotMap.mManipulator.hatchLocked;
        // }
        // RobotMap.mManipulator.lockHatch(RobotMap.mManipulator.hatchLocked);

        // RobotMap.mManipulator.setWristAngle(StateManager.targetAngle);
        // SmartDashboard.putNumber("target", StateManager.targetAngle);

        // //DRIVE
        RobotMap.mDrivebase.driveByJoystick();
        // /************************MAIN CODE***************/
        // /***********IN PROGRESS, DO NOT DELETE THIS*************/
        // TEST METHODS
        RobotMap.mManipulator.updateSensors();
        SmartDashboard.putNumber("Height cm", RobotMap.mElevator.getHeightInCM());
        RobotMap.mElevator.updateSensors();
        RobotMap.mElevator.printSensorValues();
        SmartDashboard.putBoolean("at lowest pos", RobotMap.mElevator.isAtLowestPosition());
        SmartDashboard.putBoolean("at highest pos", RobotMap.mElevator.isAtHighestPosition());
        SmartDashboard.putBoolean("arm up lim", RobotMap.mManipulator.upperLimit.isPressed());
        SmartDashboard.putBoolean("arm low lim", RobotMap.mManipulator.lowerLimit.isPressed());
        // RobotMap.mElevator.joystickControl(-Controls.testGamepad.getRawAxis(1));
        // RobotMap.mManipulator.joystickControl(-Controls.testGamepad.getRawAxis(5));
        if (RobotMap.mElevator.isAtLowestPosition()) {
            RobotMap.mElevator.zero();
        }
        RobotMap.mManipulator.autoCalibrate();
        // RobotMap.mElevator.brakeElevator(!Controls.testGamepad.getRawButton(1));
        SmartDashboard.putNumber("ele input", RobotMap.mElevator.limitCheck(Controls.testGamepad.getRawAxis(1)));
        SmartDashboard.putNumber("input", RobotMap.mManipulator.limitCheck(Controls.testGamepad.getRawAxis(5)));
        SmartDashboard.putNumber("joy in", Controls.testGamepad.getRawAxis(5));
        SmartDashboard.putNumber("feed forward", RobotMap.mManipulator.getFeedForward(false)); //set to false for testing
        SmartDashboard.putNumber("wrist motor output", RobotMap.mManipulator.wrist.get());
        SmartDashboard.putNumber("target angle", StateManager.targetAngle);
        // CHANGE MODE
        // comment if not playing hatch
        // if (Controls.CARGO_MODE.get()) {
        //     StateManager.mode = MODE.CARGO;
        // } else if (Controls.HATCH_MODE.get()) {
        //     StateManager.mode = MODE.HATCH;
        // }

        // CHANGE LEVEL
        if (Controls.LEVEL_2.get()) {
            StateManager.level = 2;
            StateManager.controlMode = CONTROL_MODE.AUTO;
        } else if (Controls.LEVEL_1.get()) {
            StateManager.level = 1;
            StateManager.controlMode = CONTROL_MODE.AUTO;
        } else if (Controls.LEVEL_3.get()) {
            StateManager.level = 3;
            StateManager.controlMode = CONTROL_MODE.AUTO;
        } else if (Controls.CARGOSHIP.get()) {
            StateManager.level = 4;
            StateManager.controlMode = CONTROL_MODE.AUTO;
        } else if (Controls.CARGO_GROUND.get()) {
            StateManager.level = 5;
            StateManager.controlMode = CONTROL_MODE.AUTO;
        } else if (Controls.RESET_ELE_WRIST.get()) {
            StateManager.level = 6;
            StateManager.controlMode = CONTROL_MODE.AUTO;
        }
        // MANUAL CONTROL TRIGGER
        if (Controls.MANUAL_ELEVATOR_TRIG.uniquePress()) {
            StateManager.controlMode = CONTROL_MODE.MANUAL;
        }

        // ENDGAME TRIGGER
        if (Controls.ENDGAME.uniquePress()) {
            StateManager.controlMode = CONTROL_MODE.ENDGAME;
        }
        //print mode to the screen 
        // String elevatorAndWristMode;
        // if (StateManager.manualMode == MANUAL_MODE.INACTIVE) {
        //     elevatorAndWristMode = "AUTO";
        // } else if(StateManager.manualMode == MANUAL_MODE.ELEVATOR) {
        //     elevatorAndWristMode = "ELEVATOR";
        // } else {
        //     elevatorAndWristMode = "WRIST/TAY";
        // }
        SmartDashboard.putNumber("level", StateManager.level);
        // SmartDashboard.putBoolean("mode", StateManager.mode);

        // SET HEIGHT & ARM ANGLE
        
        SmartDashboard.putNumber("targetHeight", StateManager.targetHeight);
        SmartDashboard.putNumber("targetAngle", StateManager.targetAngle);
        SmartDashboard.putBoolean("height tolerable", Utils.aeq(RobotMap.mElevator.getHeightInCounts(), StateManager.targetHeight, RobotMap.mElevator.heightToCounts(ElevatorConstants.tolerance)));
        // CONTROL ELEVATOR AND MANIPULATOR
        // calib
        // if (Controls.testGamepad.getRawButton(3)) {
        //     StateManager.controlMode = CONTROL_MODE.WRIST_CALIBRATE;
        // }
        // Comment this for testing
        if (StateManager.controlMode == CONTROL_MODE.MANUAL) {
            // main code
            RobotMap.mElevator.joystickControl(-Controls.testGamepad.getRawAxis(1));
            RobotMap.mManipulator.joystickTest(-Controls.testGamepad.getRawAxis(5));
        } else if (StateManager.controlMode == CONTROL_MODE.AUTO) {
            // StateManager.targetHeight = RobotMap.mElevator.getTargetHeight(StateManager.level, StateManager.mode);
            // StateManager.targetAngle = RobotMap.mManipulator.getFireAngle(StateManager.level, StateManager.mode);
            if (StateManager.mode == MODE.CARGO) {
                if (StateManager.level == 1) {
                    StateManager.targetHeight = ElevatorConstants.CARGO_level1Height;
                    StateManager.targetAngle = ManipulatorConstants.CARGO_LV1Angle;
                    StateManager.limitDown = true;
                    StateManager.limitUp = true;
                } else if (StateManager.level == 2) {
                    StateManager.targetHeight = ElevatorConstants.CARGO_level2Height;
                    StateManager.targetAngle = ManipulatorConstants.CARGO_LV2Angle;
                    StateManager.limitDown = true;
                    StateManager.limitUp = true;
                } else if (StateManager.level == 3) {
                    StateManager.targetHeight = ElevatorConstants.CARGO_level3Height;
                    StateManager.targetAngle = ManipulatorConstants.CARGO_LV3Angle;
                    StateManager.limitDown = true;
                    StateManager.limitUp = true;
                } else if (StateManager.level == 4) {
                    StateManager.targetHeight = ElevatorConstants.CARGO_shipHeight;
                    StateManager.targetAngle = ManipulatorConstants.CARGO_shipAngle;
                    StateManager.limitDown = true;
                    StateManager.limitUp = true;
                } else if (StateManager.level == 5) {
                    StateManager.targetHeight = ElevatorConstants.CARGO_groundIntakeHeight;
                    StateManager.targetAngle = ManipulatorConstants.CARGO_groundIntakeAngle;
                    StateManager.limitDown = true;
                    StateManager.limitUp = true;
                } else if (StateManager.level == 6) {
                    StateManager.targetHeight = 0;
                    StateManager.targetAngle = 94.5;
                    StateManager.limitDown = true;
                    StateManager.limitUp = true;
                }
                RobotMap.mManipulator.setWristAngle(StateManager.targetAngle);
                if (StateManager.level != 6) {
                    RobotMap.mElevator.joystickControlWithLimits(-Controls.testGamepad.getRawAxis(1), StateManager.limitUp, StateManager.limitDown);    
                } else {
                    RobotMap.mElevator.joystickControlWithLimits(-1, StateManager.limitUp, StateManager.limitDown);    
                }
                
            } 
        } else if (StateManager.controlMode == CONTROL_MODE.ENDGAME) {
            RobotMap.mClimber.puller.set(-Controls.testGamepad.getRawAxis(1));
        }
		SmartDashboard.putBoolean("ele on target", RobotMap.mElevator.isOnTarget());

        //  INTAKE & FIRE
        if (StateManager.mode == MODE.CARGO) {
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
            SmartDashboard.putString("mode", "CARGO");
        } else {
            if (Controls.INTAKE.get()) {
                StateManager.hatchLock = true;
            } else if (Controls.FIRE.get()) {
                StateManager.hatchLock = false;
            }
        // HATCH
            // RobotMap.mManipulator.lockHatch(StateManager.hatchLock);
            // SmartDashboard.putString("mode", "HATCH");
        }
        // CSVLogger.save("/home/lvuser/ele.csv", new double[] {RobotMap.mElevator.getHeightInCM(), (double) RobotMap.mElevator.masterMotor.getSelectedSensorPosition(), (double) RobotMap.mElevator.masterMotor.getSelectedSensorVelocity(), (RobotMap.mElevator.masterMotor.getSelectedSensorVelocity() - RobotMap.mElevator.lastVelocity) / 0.02, RobotMap.mElevator.masterMotor.getMotorOutputPercent(), Timer.getFPGATimestamp()});
        // RobotMap.mElevator.lastVelocity = RobotMap.mElevator.masterMotor.getSelectedSensorVelocity();
    }

}
