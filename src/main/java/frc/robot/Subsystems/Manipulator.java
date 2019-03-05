
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
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.Utils;
import frc.robot.RobotLoop.StateManager;

public class Manipulator extends Subsystem {

  //---HARDWARE---//

  // VictorSP wrist = new VictorSP(RobotMap.WRIST_VICTOR);
  WPI_TalonSRX wrist = new WPI_TalonSRX(1);
  AnalogInput wristEncoder = new AnalogInput(RobotMap.ANALOG_ENCODER);

  // VictorSP upperWheel = new VictorSP(RobotMap.UPPER_INTAKE);
  // VictorSP lowerWheel = new VictorSP(RobotMap.LOWER_INTAKE);
  TalonSRX intake = new TalonSRX(2);

  //normally closed switch, so it will return true when pressed
  DigitalInput cargoDetect = new DigitalInput(RobotMap.CARGO_SWITCH);

  Solenoid hatchIntake = new Solenoid(RobotMap.HATCH_SOLENOID);

  //--------------//

  /**
   * @return returns current wrist angle in degrees, with horizontal position at 0
   */
  private double getWristAngle(){
    double analogValue = wristEncoder.getAverageValue();
    double angle = (analogValue - ArmConstants.ma3MinValue) * 360 / (ArmConstants.ma3MaxValue - ArmConstants.ma3MinValue);
    angle = (angle == 360) ? 0 : angle;
    return angle - ArmConstants.angleOffset;
  }

  /**
	 * @param angle angle to set arm to, fully upwards is a, fully downwards is b.
	 */
  public void setWristAngle(double angle){
    double ERROR = angle - getWristAngle();
    double kP;
    double output; 

    if (ERROR > 0) {
      kP = ArmConstants.kP_UP;
      output = Utils.limitNumber((ERROR) * kP, -ArmConstants.maxIntakeOutput, ArmConstants.maxIntakeOutput);
    } else {
      kP = ArmConstants.kP_DOWN;
      output = Utils.limitNumber((ERROR) * kP, -ArmConstants.maxIntakeOutput, ArmConstants.maxIntakeOutput);
    }
    wrist.set(output);
  }

  public boolean wristOnTarget(){
    return Math.abs(StateManager.desiredWristAngle - getWristAngle()) <= ArmConstants.angleTolerance;
  }

  /**
   * @param IN 
   */
  public void cargoIntake(){
    double speed = ArmConstants.maxIntakeOutput;
    // upperWheel.set(speed);
    intake.set(ControlMode.PercentOutput, speed);
  }

  public void cargoFire() {
    double speed = ArmConstants.maxFireOutput;
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
    if (IN) {
      hatchIntake.set(true);
    } else {
      hatchIntake.set(false);
    }
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
    SmartDashboard.putNumber("arm angle wout offset", getWristAngle());
    SmartDashboard.putBoolean("intake", cargoDetect.get());
    SmartDashboard.putNumber("ma3 raw val", wristEncoder.getAverageVoltage());
    SmartDashboard.putNumber("angle", getWristAngle());
  }

  public double getFeedForward() {
    return 1;
  }

  @Override
  public void initDefaultCommand() {
  }
}
