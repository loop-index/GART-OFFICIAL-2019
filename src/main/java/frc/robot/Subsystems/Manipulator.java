
package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.Constants.ArmConstants;
import frc.robot.RobotLoop.StateManager;

public class Manipulator extends Subsystem {

  //---HARDWARE---//

  VictorSP wrist = new VictorSP(RobotMap.WRIST_VICTOR);
  AnalogInput wristEncoder = new AnalogInput(RobotMap.ANALOG_ENCODER);

  VictorSP upperWheel = new VictorSP(RobotMap.UPPER_INTAKE);
  VictorSP lowerWheel = new VictorSP(RobotMap.LOWER_INTAKE);

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
    double maxSpeed = 0.7;
    double kP = maxSpeed/ArmConstants.rangeOfMovement;

    wrist.set(ERROR * kP);
  }

  public boolean wristOnTarget(){
    return Math.abs(StateManager.desiredWristAngle - getWristAngle()) <= ArmConstants.angleTolerance;
  }

  /**
   * @param IN 
   */
  public void cargoIntake(boolean IN){
    double speed = 1;
    if (IN){
      if (cargoDetect.get()){
        upperWheel.set(speed);
        lowerWheel.set(-speed);
      } else {
        upperWheel.stopMotor();
        lowerWheel.stopMotor();
      }
    } else {
      upperWheel.set(-speed);
      lowerWheel.set(speed);
    }
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

  @Override
  public void initDefaultCommand() {
  }
}
