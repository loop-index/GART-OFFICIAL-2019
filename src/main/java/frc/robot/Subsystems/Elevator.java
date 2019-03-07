
package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.Utils;

public class Elevator extends Subsystem {
  //actuators
//   public TalonSRX masterMotor = new TalonSRX(5);
// 	public TalonSRX slaveMotor = new TalonSRX(6);
	
// 	public Solenoid elevatorBrake = new Solenoid(2); //fixed
// 	public Solenoid gearShifter = new Solenoid(1); //needs update
	
// 	//limit switch
// 	public DigitalInput topStage1Switch = new DigitalInput(6);
// 	public DigitalInput armBottomSwitch = new DigitalInput(8);
// 	public DigitalInput armTopSwitch = new DigitalInput(7);	
// 	public DigitalInput bottomStage1Switch = new DigitalInput(5);
	
	
// 	DigitalInput[] switches = {topStage1Switch, armBottomSwitch, armTopSwitch, bottomStage1Switch};
// 	//

// 	//robot status
// 	boolean[] limitSwitchStates = new boolean[4];
// 	boolean[] lastlimitSwitchStates = new boolean[4];

// 	public String limitSwitchStatesString = "";
// 	String lastLimitSwitchStatesString = "";

// 	double currentPosition;
// 	double targetPosition; //in raw unit
// 	int currentStage; //1 or 2
// 	boolean isMoving = false;

// 	public Elevator() {
// 		//init
// 		//elevator config
// 		slaveMotor.follow(masterMotor);

// 		masterMotor.set(ControlMode.PercentOutput, 0);

// 		masterMotor.configFactoryDefault();
// 		slaveMotor.configFactoryDefault();

// 		masterMotor.setNeutralMode(NeutralMode.Brake);
// 		slaveMotor.setNeutralMode(NeutralMode.Brake);

// 		masterMotor.setSensorPhase(false); //fixed 
// 		masterMotor.setSelectedSensorPosition((int) heightToCounts(ElevatorConstants.kDeadBand));

// 		masterMotor.setInverted(true); //fixed
// 		slaveMotor.setInverted(true);

// 		masterMotor.configNeutralDeadband(ElevatorConstants.kNeutralDeadband);
// 		slaveMotor.configNeutralDeadband(ElevatorConstants.kNeutralDeadband);
	
// 		// masterMotor.configMotionCruiseVelocity(ElevatorConstants.kCruiseVelocity);
// 		// masterMotor.configMotionAcceleration(ElevatorConstants.kAcceleration);

// 		//PID config
// 		masterMotor.config_kP(ElevatorConstants.kSlot_Distanc, ElevatorConstants.kGains_Distanc.kP, ElevatorConstants.kTimeoutMs);
// 		masterMotor.config_kI(ElevatorConstants.kSlot_Distanc, ElevatorConstants.kGains_Distanc.kI, ElevatorConstants.kTimeoutMs);
// 		masterMotor.config_kD(ElevatorConstants.kSlot_Distanc, ElevatorConstants.kGains_Distanc.kD, ElevatorConstants.kTimeoutMs);
// 		masterMotor.config_kF(ElevatorConstants.kSlot_Distanc, ElevatorConstants.kGains_Distanc.kF, ElevatorConstants.kTimeoutMs);
// 		masterMotor.config_IntegralZone(ElevatorConstants.kSlot_Distanc, ElevatorConstants.kGains_Distanc.kIzone, ElevatorConstants.kTimeoutMs);
// 		masterMotor.configClosedLoopPeakOutput(ElevatorConstants.kSlot_Distanc, ElevatorConstants.kGains_Distanc.kPeakOutput, ElevatorConstants.kTimeoutMs);
// 		masterMotor.configAllowableClosedloopError(ElevatorConstants.kSlot_Distanc, 0, ElevatorConstants.kTimeoutMs);

// 		masterMotor.selectProfileSlot(ElevatorConstants.kSlot_Distanc, 0);

// 		// currentStage = 1; //when starts, arm is in lowest position so stage is 1

// 		//brake config
// 		elevatorBrake.set(false); //when starts, brake for safety

// 		//gear shifter config
// 		gearShifter.set(false); //starts with high gear 22.67
	
// 		//first limit switches calibration
// 		updateSensors();
// 		System.arraycopy(limitSwitchStates, 0, lastlimitSwitchStates, 0, 4);
// 		lastLimitSwitchStatesString = limitSwitchStatesString; 
// 	}

// 	//first elevator config method, use in disablePeriodic
// 	public void disableModeControl() {
// 		elevatorBrake.set(false);
// 		gearShifter.set(false);
// 	}

// 	public void joystickTest() {
// 		// masterMotor.set(ControlMode.PercentOutput, limitCheck(Utils.deadband(-Robot.oi.joystick.getRawAxis(5), 0.1)*0.8));
// 	}

// 	//main control methods
// 	public void autoElevator() {
// 		// if (Robot.op.isLV1) {
// 		// 	setTargetPosition_PositionPID(ElevatorConstants.CARGO_level1Height);
			
// 		// }
// 	}

// 	public boolean isOnTarget() {
// 		return Utils.aeq(getHeightInCounts(), targetPosition, 1);
// 	}

// 	public void brakeElevator(boolean enable) {
// 		elevatorBrake.set(!enable);
// 	}

// 	public void shiftGear() {
// 		gearShifter.set(!gearShifter.get());
// 	}

// 	public void setTargetPosition_MotionMagic(double targetPosition) {
// 		this.targetPosition = targetPosition;

// 		masterMotor.set(ControlMode.MotionMagic, targetPosition, DemandType.ArbitraryFeedForward, 0);
// 	}

// 	public void setTargetPosition_PositionPID(double targetPosition) {//target height must be in raw units
// 		this.targetPosition = targetPosition;
		
// 		masterMotor.set(ControlMode.Position, targetPosition, DemandType.ArbitraryFeedForward, 0);
// 		slaveMotor.follow(masterMotor);
// 	}

// 	//sensor methods
// 	//call updateSensors in periodic
// 	public void updateSensors() {
// 		lastLimitSwitchStatesString = limitSwitchStatesString;
// 		limitSwitchStatesString = "";
// 		System.arraycopy(limitSwitchStates, 0, lastlimitSwitchStates, 0, 4);
// 		for (int i = 0; i < 4; i++) {
// 			limitSwitchStates[i] = switches[i].get();
// 			if (limitSwitchStates[i]) {
// 				limitSwitchStatesString += "1";
// 			} else {
// 				limitSwitchStatesString += "0";
// 			}
// 		}
// 		currentPosition = masterMotor.getSelectedSensorPosition();
// 	}

// 	public void printSensorValues() {
// 		// System.out.println(limitSwitchStatesString);
// 		SmartDashboard.putString("switch states", limitSwitchStatesString);
// 	}

// 	public void zero() {
// 		// targetPosition = (int) heightToCounts(ElevatorConstants.kDeadBand);
// 		masterMotor.setSelectedSensorPosition((int) heightToCounts(ElevatorConstants.kDeadBand));
// 		// masterMotor.setSelectedSensorPosition(0);
// 		// currentPosition = targetPosition;
// 	}

// 	public void calibrateElevator() { //run to lowest position
// 		// if (limitSwitchStatesString = "001") {
// 		// 	Stage = 1;
// 		// }
		
// 	}

// 	public void runToLowestPosition() {
// 		masterMotor.set(ControlMode.PercentOutput, ElevatorConstants.zeroingOutput);
// 		// return isAtLowestPosition();
// 	}

// 	//safety methods
// 	public boolean isAtLowestPosition() {
// 		return limitSwitchStatesString.equals(ElevatorConstants.LOWEST_SWITCH_POSITION) || !armBottomSwitch.get();
// 	}

// 	public boolean isAtHighestPosition() {
// 		return limitSwitchStatesString.equals(ElevatorConstants.HIGHEST_SWITCH_POSITION) || !armTopSwitch.get();
// 	}

// 	public double limitCheck(double input) {
// 		if (isAtLowestPosition() && input < 0) {
// 			return 0;
// 		} else if (isAtHighestPosition() && input > 0) {
// 			return 0;
// 		} else {
// 			return input;
// 		}
// 	}

// 	//get methods
// public double getDesiredHeight (int level, boolean cargoMode){
//   int mode = (cargoMode) ? 0 : 1;
//   return ElevatorConstants.setElevatorHeight [mode][level - 1];
// }

// 	public double getHeightInCounts() {
// 		return masterMotor.getSelectedSensorPosition();
// 	}

// 	//not useful until now thanks to the brake
// 	public double getFeedForwardOfStage(int stage) {
// 		return 1;
// 	}

// 	//math/conversion methods
// 	public double heightToCounts(double height) {
// 		return height * ElevatorConstants.kCountsPerRevSRX / 2 / Math.PI / ElevatorConstants.kEncGearRatio / (ElevatorConstants.kChainWheelDiameter/2);
// 	}

// 	public double countsToHeight(double counts) {
// 		return Utils.countsToRad(counts, ElevatorConstants.kCountsPerRevSRX)*ElevatorConstants.kEncGearRatio*ElevatorConstants.kChainWheelDiameter/2;
// 	}



  @Override
  public void initDefaultCommand() {
  }
}
