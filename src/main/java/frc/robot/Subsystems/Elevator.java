
package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Elevator extends Subsystem {

  public WPI_TalonSRX masterMotor = new WPI_TalonSRX(RobotMap.CAN_ELEVATOR_MASTER);
	public WPI_TalonSRX slaveMotor = new WPI_TalonSRX(RobotMap.CAN_ELEVATOR_FOLLOW);
	
	public Solenoid elevatorBrake = new Solenoid(RobotMap.BRAKE_SOLENOID); //fixed
	public Solenoid gearShifter = new Solenoid(RobotMap.GEARSHIFT_SOLENOID); //needs update
	
	public DigitalInput topStage1Switch = new DigitalInput(RobotMap.STAGE1_UPPER_SWITCH);
	public DigitalInput bottomStage1Switch = new DigitalInput(RobotMap.STAGE1_LOWER_SWITCH);
	public DigitalInput armBottomSwitch = new DigitalInput(RobotMap.STAGE2_LOWER_SWITCH);
  public DigitalInput armTopSwitch = new DigitalInput(RobotMap.STAGE2_UPPER_SWITCH);	
  
  DigitalInput[] switches = {topStage1Switch, armBottomSwitch, armTopSwitch, bottomStage1Switch};

  private int curLevel = 1;
  private boolean cargoMode = true;
  



  @Override
  public void initDefaultCommand() {
  }
}
