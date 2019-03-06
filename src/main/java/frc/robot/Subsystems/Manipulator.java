
package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.Constants.ManipulatorConstants;
import frc.robot.Constants.Utils;
import frc.robot.RobotLoop.StateManager;

public class Manipulator extends Subsystem {

  //---HARDWARE---//

  // VictorSP wrist = new VictorSP(RobotMap.WRIST_VICTOR);
  // WPI_TalonSRX wrist = new WPI_TalonSRX(2);
  // VictorSP wrist = new VictorSP(RobotMap.WRIST_VICTOR);
  WPI_TalonSRX wrist = new WPI_TalonSRX(1);
  AnalogInput wristEncoder = new AnalogInput(RobotMap.ANALOG_ENCODER);

  // VictorSP intake = new VictorSP(RobotMap.UPPER_INTAKE);
  TalonSRX intake = new TalonSRX(2);

  //normally closed switch, so it will return true when pressed
  DigitalInput cargoDetect = new DigitalInput(RobotMap.CARGO_SWITCH);

  // Solenoid hatchIntake = new Solenoid(RobotMap.HATCH_SOLENOID);

  //--------------//
  //variables
  double momentOfGravity;
  double ffVoltage;

  double targetAngle;
  double ERROR;
  double totalError;

  public Manipulator() {
    targetAngle = getWristAngle();
    ERROR = targetAngle - getWristAngle();
  }

  /**
   * @return returns current wrist angle in degrees, with horizontal position at 0
   */
  public double getWristAngle(){
    double analogValue = wristEncoder.getAverageValue();
    double angle = (analogValue - ManipulatorConstants.ma3MinValue) * 360 / (ManipulatorConstants.ma3MaxValue - ManipulatorConstants.ma3MinValue);
    angle = (angle == 360) ? 0 : angle;
    return angle - ManipulatorConstants.angleOffset;
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
    targetAngle = angle;
    ERROR = targetAngle - getWristAngle();
    totalError += ERROR*0.02;
    double kP;
    double output; 

    if (ERROR > 0) {
      kP = ManipulatorConstants.kP_UP;
      output = Utils.limitNumber((ERROR) * kP, -ManipulatorConstants.maxManipulatorOutput, ManipulatorConstants.maxManipulatorOutput);
    } else {
      kP = ManipulatorConstants.kP_DOWN;
      output = Utils.limitNumber((ERROR) * kP, -ManipulatorConstants.maxManipulatorOutput, ManipulatorConstants.maxManipulatorOutput);
    }
    wrist.set(output);
    
    //reset integral
    if (wristOnTarget()) {
      totalError = 0;
    }
    //debug
    SmartDashboard.putNumber("total error", totalError);
  }

  public boolean wristOnTarget(){
    return Utils.aeq(ERROR, targetAngle, ManipulatorConstants.angleTolerance);
  }

  public void cargoIntake(){
    double speed = ManipulatorConstants.maxIntakeOutput;
    intake.set(ControlMode.PercentOutput, speed);
  }

  public void cargoFire() {
    double speed = ManipulatorConstants.maxFireOutput;
    intake.set(ControlMode.PercentOutput, speed);
  }

  /**
   * @return if the switch has been depressed by cargo
   */
  public boolean isCargoIn(){
    return cargoDetect.get();
  }

  /**
   * @param IN
   */
  public void hatchIntake(boolean IN){
    // if (IN) {
    //   hatchIntake.set(true);
    // } else {
    //   hatchIntake.set(false);
    // }
  }

  /**
   * Stops motors.
   */
  public void stopCargoWheels(){
    // upperWheel.stopMotor();
    // lowerWheel.stopMotor();

    intake.set(ControlMode.PercentOutput, 0);
  }

  //sensor methods
  public void updateSensors() {
    SmartDashboard.putBoolean("cargo detected", isCargoIn());
    SmartDashboard.putNumber("arm angle wout offset", getWristAngleWithoutOffset());
    SmartDashboard.putBoolean("intake", cargoDetect.get());
    SmartDashboard.putNumber("ma3 raw val", wristEncoder.getAverageVoltage());
    SmartDashboard.putNumber("angle", getWristAngle());
  }

  //needs more data to complete
  public double getFeedForward() {
    //function: T = 31/250 + 89/1750 * V
    momentOfGravity = (ManipulatorConstants.manipulatorWeight * ManipulatorConstants.g * ManipulatorConstants.manipulatorLever * Math.cos(Utils.d2r(getWristAngle()))) / ManipulatorConstants.totalRatio;
    ffVoltage = (momentOfGravity - 31/250)/(89/1750);
    return ffVoltage/12;
  }

  @Override
  public void initDefaultCommand() {
  }
}
