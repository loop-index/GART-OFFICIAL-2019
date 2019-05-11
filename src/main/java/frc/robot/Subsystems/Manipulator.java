
package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.LimitSwitch;
import frc.robot.RobotMap;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.ManipulatorConstants;
import frc.robot.Constants.Utils;
import frc.robot.RobotLoop.StateManager;
import frc.robot.RobotLoop.StateManager.MODE;

public class Manipulator extends Subsystem {

  //---HARDWARE---//

  public VictorSP wrist = new VictorSP(RobotMap.WRIST_VICTOR);
  // WPI_TalonSRX wrist = new WPI_TalonSRX(2);
  // WPI_TalonSRX wrist = new WPI_TalonSRX(1);
  AnalogInput wristEncoder = new AnalogInput(RobotMap.ANALOG_ENCODER);

  VictorSP intake = new VictorSP(RobotMap.UPPER_INTAKE);
  // TalonSRX intake = new TalonSRX(2);

  //normally closed switch, so it will return true when pressed
  public LimitSwitch cargoDetect = new LimitSwitch(RobotMap.CARGO_SWITCH, false); //NC
  public LimitSwitch upperLimit = new LimitSwitch(0, false); //NC
  public LimitSwitch lowerLimit = new LimitSwitch(1, false); //NC

  Solenoid hatchIntake = new Solenoid(RobotMap.HATCH_SOLENOID);
  // DoubleSolenoid hatchIntake = new DoubleSolenoid(0, 1);

  //--------------//
  //variables
  public boolean hatchLocked;

  public double horizontalAngle = ManipulatorConstants.angleOffset;
  public double upperLimitAngle = ManipulatorConstants.upperLimit;
  public double lowerLimitAngle = ManipulatorConstants.lowerLimit;

  // double targetAngle;
  double ERROR;
  double totalError;

  public Manipulator() {
    StateManager.targetAngle = getWristAngle();
    ERROR = StateManager.targetAngle - getWristAngle();
    hatchLocked = false;
  }

  public void joystickControl(double input) {
		double processedInput = limitCheck(Utils.deadband(input, 0.1)*0.45);
		// double processedInput = Utils.deadband(input, 0.1)*0.8;
    if (processedInput != 0) {
      wrist.set(processedInput + getFeedForward(true));
      // wrist.set(processedInput);
      StateManager.changedFromMoveToHold = false;
    } else {
      if (!StateManager.changedFromMoveToHold) {
        StateManager.changedFromMoveToHold = true;
        StateManager.targetAngle = getWristAngle();
      } 
      setWristAngle(StateManager.targetAngle);
      // setWristAngleWithoutFeedforward(StateManager.targetAngle);

    }
	}

  public void joystickTest(double input) {
		double processedInput = limitCheck(Utils.deadband(input, 0.1)*0.6);
		// double processedInput = Utils.deadband(input, 0.1)*0.8;
    if (processedInput != 0) {
      wrist.set(processedInput);
      // wrist.set(processedInput);
      StateManager.changedFromMoveToHold = false;
    } else {
      if (!StateManager.changedFromMoveToHold) {
        StateManager.changedFromMoveToHold = true;
        StateManager.targetAngle = getWristAngle();
        stopWrist();
      } 
      // setWristAngle(StateManager.targetAngle);
      // setWristAngleWithoutFeedforward(StateManager.targetAngle);

    }
	}

  /**
   * @return returns current wrist angle in degrees, with horizontal position at 0
   */
  public double getWristAngle(){
    double analogValue = wristEncoder.getAverageValue();
    double angle = (analogValue - ManipulatorConstants.ma3MinValue) * 360 / (ManipulatorConstants.ma3MaxValue - ManipulatorConstants.ma3MinValue);
    angle = (angle == 360) ? 0 : angle;
    return angle - horizontalAngle;
  }

  public double getWristAngleWithoutOffset() {
    double analogValue = wristEncoder.getAverageValue();
    double angle = (analogValue - ManipulatorConstants.ma3MinValue) * 360 / (ManipulatorConstants.ma3MaxValue - ManipulatorConstants.ma3MinValue);
    angle = (angle == 360) ? 0 : angle;
    return angle;
  }

  /**
	 * @param angle angle to set arm to, fully upwards is a, fully downwards is b.
	 */
  public void setWristAngle(double angle){
    // double ERROR = angle - getWristAngle();
    StateManager.targetAngle = angle;
    ERROR = StateManager.targetAngle - getWristAngle();
    totalError += ERROR*0.02;
    double kP;
    double output; 

    if (ERROR > 0) {
      kP = ManipulatorConstants.kP_UP;
      // kP = (ERROR < ManipulatorConstants.smallError && ERROR > ManipulatorConstants.epsilon) ? kP : kP*2;
      output = Utils.limitNumber((ERROR) * kP, -ManipulatorConstants.maxManipulatorOutput, ManipulatorConstants.maxManipulatorOutput);
    } else {
      kP = ManipulatorConstants.kP_DOWN;
      output = Utils.limitNumber((ERROR) * kP, -ManipulatorConstants.maxManipulatorOutput, ManipulatorConstants.maxManipulatorOutput);
    }
    
    // output = ((RobotMap.mElevator.isAtGroundCargoHeight() || RobotMap.mElevator.getHeightInCM() < ElevatorConstants.CARGO_groundIntakeHeight) && output < 0 && (!Utils.aeq(getWristAngleWithoutOffset(), horizontalAngle, ManipulatorConstants.manualControlTolerance))) ? 0: output;

    wrist.set(output);
    
    if (wristOnTarget()) {
      totalError = 0;
    }
    //debug
    SmartDashboard.putNumber("total error", totalError);
  }

  public void setWristAngleWithoutFeedforward(double angle){
    // double ERROR = angle - getWristAngle();
    StateManager.targetAngle = angle;
    ERROR = StateManager.targetAngle - getWristAngle();
    double kP;
    double output; 

    if (ERROR > 0) {
      kP = ManipulatorConstants.kP_UP;
      // kP = (ERROR < ManipulatorConstants.smallError && ERROR > ManipulatorConstants.epsilon) ? kP : kP*2;
      output = Utils.limitNumber((ERROR) * kP, -ManipulatorConstants.maxManipulatorOutput, ManipulatorConstants.maxManipulatorOutput);
    } else {
      kP = ManipulatorConstants.kP_DOWN;
      output = Utils.limitNumber((ERROR) * kP, -ManipulatorConstants.maxManipulatorOutput, ManipulatorConstants.maxManipulatorOutput);
    }
    if (wristOnTarget() || getWristAngle() > 90) {
      output = 0;
    }
    wrist.set(output);
  }
    
  //   //debug
  //   SmartDashboard.putNumber("total error", totalError);
  // }

  /**
   * @return true if the actual wrist angle is within acceptable tolerance of the desired angle
   */
  public boolean wristOnTarget(){
    return Utils.aeq(ERROR, StateManager.targetAngle, ManipulatorConstants.angleTolerance);
  }

  public void cargoIntake(){
    double speed = ManipulatorConstants.maxIntakeOutput;
    intake.set(speed);
  }

  public void cargoFire() {
    double speed = ManipulatorConstants.maxFireOutput;
    intake.set(speed);
  }

  /**
   * @return if the switch has been depressed by cargo
   */
  public boolean isCargoIn(){
    return cargoDetect.isPressed();
  }

  /**
   * @param TF
   */
  public void lockHatch(boolean TF){
    hatchIntake.set(TF);
  }

  /**
   * Stops motors.
   */
  public void stopCargoWheels(){
    // intake.stopMotor();
    intake.set(0);
  }

  public void stopWrist() {
    wrist.set(0);
  }

  /**
   * Safety method(s)
   */
  public double limitCheck(double input) {
		// if (Math.abs(getWristAngleWithoutOffset() - lowerLimitAngle) <= ManipulatorConstants.manualControlTolerance && input < 0) {
		// 	return 0;
		// } else if (Math.abs((upperLimitAngle) - getWristAngleWithoutOffset()) <= ManipulatorConstants.manualControlTolerance && input > 0) {
		// 	return 0;
		// } else {
		// 	return input;
    // }
    if (Math.abs(getWristAngleWithoutOffset() - lowerLimitAngle) <= ManipulatorConstants.manualControlTolerance && input < 0) {
			return 0;
		} else if (Math.abs((upperLimitAngle) - getWristAngleWithoutOffset()) <= ManipulatorConstants.manualControlTolerance && input > 0) {
      return 0;
    }
    // } else if(((RobotMap.mElevator.isAtGroundCargoHeight() || RobotMap.mElevator.getHeightInCM() < ElevatorConstants.CARGO_groundIntakeHeight) && Utils.aeq(getWristAngleWithoutOffset(), horizontalAngle, ManipulatorConstants.manualControlTolerance)) && input < 0) {
    //   return 0;
    // } 
    else {
			return input;
		}
	}

  /**
   * Update sensors values to SmartDashboard
   */
  public void updateSensors() {
    SmartDashboard.putBoolean("cargo detected", isCargoIn());
    SmartDashboard.putNumber("arm angle wout offset", getWristAngleWithoutOffset());
    SmartDashboard.putBoolean("intake", cargoDetect.isPressed());
    SmartDashboard.putNumber("ma3 raw val", wristEncoder.getAverageVoltage());
    SmartDashboard.putNumber("angle", getWristAngle());
  }

  
  public double getFeedForward(boolean isCIMMotor) {
    //function: T(gravity) = 31/250 + 89/1750 * V
    double momentOfGravity = (ManipulatorConstants.manipulatorWeight * ManipulatorConstants.g * ManipulatorConstants.manipulatorLever * Math.cos(Utils.d2r(getWristAngle()))) / ManipulatorConstants.totalRatio;
    double ffVoltage = (-1.0)*(momentOfGravity - 31.0/250)/(89.0/1750);

    SmartDashboard.putNumber("moment", momentOfGravity);
    SmartDashboard.putNumber("ff volt", ffVoltage);
    // System.out.println(ffVoltage);
    SmartDashboard.putNumber("ff", ffVoltage/12);

    if (getWristAngle() >= 90 || getWristAngle() <= 40 || isCIMMotor) {
      ffVoltage = 0;
    }

    return ffVoltage/12;
  }

  public double getFireAngle(int level, MODE currentMode) {
    int mode = (currentMode == MODE.CARGO) ? 0 : 1;
	return ManipulatorConstants.setWristAngles [mode][level - 1];
  }

  public boolean autoCalibrate() {
    if (upperLimit.isPressed()) {
      upperLimitAngle = getWristAngleWithoutOffset();
      horizontalAngle = upperLimitAngle - 94.5;
      lowerLimitAngle = horizontalAngle - 42;
      return true;
    } else if (lowerLimit.isPressed()) {
      lowerLimitAngle = getWristAngleWithoutOffset();
      horizontalAngle = lowerLimitAngle + 42;
      upperLimitAngle = horizontalAngle + 94.5;
      return true;
    } else {
      return false;
    }
  }

  public boolean runToLowestPosition(double speed) {
    if (!lowerLimit.isPressed()) {
      wrist.set(speed);
      return false;
    }
    stopWrist();
    return true;
  }

  @Override
  public void initDefaultCommand() {
  }
}
