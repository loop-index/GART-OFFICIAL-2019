
package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.w3c.dom.css.ElementCSSInlineStyle;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.Utils;
import frc.robot.RobotLoop.StateManager;
import frc.robot.RobotLoop.StateManager.MODE;

public class Elevator extends Subsystem {
  //actuators
  public TalonSRX masterMotor = new TalonSRX(5);
	public TalonSRX slaveMotor = new TalonSRX(6);
	
	public Solenoid elevatorBrake = new Solenoid(1); //fixed
	public Solenoid gearShifter = new Solenoid(6); //needs update
	
	//limit switch
	public DigitalInput topStage1Switch = new DigitalInput(6);
	public DigitalInput armBottomSwitch = new DigitalInput(8);
	public DigitalInput armTopSwitch = new DigitalInput(7);	
	public DigitalInput bottomStage1Switch = new DigitalInput(5);
	
	
	DigitalInput[] switches = {topStage1Switch, armBottomSwitch, armTopSwitch, bottomStage1Switch};
	//

	//robot status
	boolean[] limitSwitchStates = new boolean[4];
	boolean[] lastlimitSwitchStates = new boolean[4];

	public String limitSwitchStatesString = "";
	String lastLimitSwitchStatesString = "";

	double currentPosition;
	double targetPosition; //in raw unit
	int currentStage; //1 or 2
	boolean isMoving = false;
	public double lastVelocity = 0;

	public Elevator() {
		//init
		//elevator config
		slaveMotor.follow(masterMotor);

		masterMotor.set(ControlMode.PercentOutput, 0);

		masterMotor.configFactoryDefault();
		slaveMotor.configFactoryDefault();

		masterMotor.setNeutralMode(NeutralMode.Brake);
		slaveMotor.setNeutralMode(NeutralMode.Brake);

		masterMotor.setSensorPhase(false); //fixed 
		masterMotor.setSelectedSensorPosition((int) heightToCounts(ElevatorConstants.kDeadBand));

		masterMotor.setInverted(true); //fixed
		slaveMotor.setInverted(true);

		masterMotor.configNeutralDeadband(ElevatorConstants.kNeutralDeadband);
		slaveMotor.configNeutralDeadband(ElevatorConstants.kNeutralDeadband);
	
		masterMotor.configMotionCruiseVelocity(ElevatorConstants.kCruiseVelocityRawUnits);
		masterMotor.configMotionAcceleration(ElevatorConstants.kAccelerationRawUnits);

		//PID config
		masterMotor.config_kP(ElevatorConstants.kSlot_Distanc, ElevatorConstants.kGains_Distanc.kP, ElevatorConstants.kTimeoutMs);
		masterMotor.config_kI(ElevatorConstants.kSlot_Distanc, ElevatorConstants.kGains_Distanc.kI, ElevatorConstants.kTimeoutMs);
		masterMotor.config_kD(ElevatorConstants.kSlot_Distanc, ElevatorConstants.kGains_Distanc.kD, ElevatorConstants.kTimeoutMs);
		masterMotor.config_kF(ElevatorConstants.kSlot_Distanc, ElevatorConstants.kGains_Distanc.kF, ElevatorConstants.kTimeoutMs);
		masterMotor.config_IntegralZone(ElevatorConstants.kSlot_Distanc, ElevatorConstants.kGains_Distanc.kIzone, ElevatorConstants.kTimeoutMs);
		masterMotor.configClosedLoopPeakOutput(ElevatorConstants.kSlot_Distanc, ElevatorConstants.kGains_Distanc.kPeakOutput, ElevatorConstants.kTimeoutMs);
		masterMotor.configAllowableClosedloopError(ElevatorConstants.kSlot_Distanc, 0, ElevatorConstants.kTimeoutMs);

		masterMotor.selectProfileSlot(ElevatorConstants.kSlot_Distanc, 0);

		// currentStage = 1; //when starts, arm is in lowest position so stage is 1

		//brake config
		elevatorBrake.set(false); //when starts, brake for safety

		//gear shifter config
		gearShifter.set(false); //starts with high gear 22.67
	
		//first limit switches calibration
		updateSensors();
		System.arraycopy(limitSwitchStates, 0, lastlimitSwitchStates, 0, 4);
		lastLimitSwitchStatesString = limitSwitchStatesString; 
	}

	//first elevator config method, use in disablePeriodic
	public void disableModeControl() {
		elevatorBrake.set(false);
		gearShifter.set(false);
	}

	public void joystickControl(double input) {
		double processedInput = limitCheck(Utils.deadband(input, 0.1)*0.65);
		double output = processedInput;
		if (output < 0) {
			output = output*0.2;
		}
		if (output == 0) {
			brakeElevator(true);
		} else {
			brakeElevator(false);
		}
		masterMotor.set(ControlMode.PercentOutput, output);
	}

	public void joystickControlWithLimits(double input, boolean limitUp, boolean limitDown) {
		double processedInput = limitCheck(Utils.deadband(input, 0.1)*0.55);
		double output = processedInput;
		if (isOnTarget()) {
			if ((limitDown && processedInput < 0) || (limitUp && processedInput > 0)) {
				output = 0;
			} 
		}
		if (output < 0) {
			output = output*0.2;
		}
		if (output == 0) {
			brakeElevator(true);
		} else {
			brakeElevator(false);
		}
		masterMotor.set(ControlMode.PercentOutput, output);
	}

	// mechanism removed
	/*** 
	public void climb(double input) {
		double processedInput = limitCheck(Utils.deadband(input, 0.1));
		double output = processedInput;
		if (output == 0) {
			brakeElevator(true);
		} else {
			brakeElevator(false);
		}
		masterMotor.set(ControlMode.PercentOutput, output);
	}

	***/

	public boolean isOnTarget() {
		return Utils.aeq(masterMotor.getSelectedSensorPosition(), heightToCounts(StateManager.targetHeight), heightToCounts(ElevatorConstants.tolerance));
	}

	public boolean isAtGroundCargoHeight() {
		return Utils.aeq(masterMotor.getSelectedSensorPosition(), heightToCounts(ElevatorConstants.CARGO_groundIntakeHeight), heightToCounts(ElevatorConstants.tolerance));
	}

	public void brakeElevator(boolean enable) {
		elevatorBrake.set(!enable);
	}

	public void shiftGear() {
		gearShifter.set(!gearShifter.get());
	}

	public void setTargetPosition_MotionMagic(double targetPosition) {
		this.targetPosition = targetPosition;

		
		if (!isOnTarget()) {
			masterMotor.set(ControlMode.MotionMagic, targetPosition, DemandType.ArbitraryFeedForward, 0);

		}
	}

	public void setTargetPosition_PositionPID(int targetPosition) {//target height must be in raw units
		this.targetPosition = targetPosition;
		
		if (!Utils.aeq(masterMotor.getSelectedSensorPosition(), targetPosition, heightToCounts(ElevatorConstants.tolerance))) {
			brakeElevator(false);
			masterMotor.set(ControlMode.Position, targetPosition);
		} else {
			masterMotor.set(ControlMode.PercentOutput, 0);
			brakeElevator(true);
		}
	}

	//sensor methods
	//call updateSensors in periodic
	public void updateSensors() {
		lastLimitSwitchStatesString = limitSwitchStatesString;
		limitSwitchStatesString = "";
		System.arraycopy(limitSwitchStates, 0, lastlimitSwitchStates, 0, 4);
		for (int i = 0; i < 4; i++) {
			limitSwitchStates[i] = switches[i].get();
			if (limitSwitchStates[i]) {
				limitSwitchStatesString += "1";
			} else {
				limitSwitchStatesString += "0";
			}
		}
		currentPosition = masterMotor.getSelectedSensorPosition();
	}

	public void printSensorValues() {
		// System.out.println(limitSwitchStatesString);
		SmartDashboard.putString("switch states", limitSwitchStatesString);
	}

	public void zero() {
		// targetPosition = (int) heightToCounts(ElevatorConstants.kDeadBand);
		masterMotor.setSelectedSensorPosition(0);
		// masterMotor.setSelectedSensorPosition(0);
		// currentPosition = targetPosition;
	}

	public void calibrateElevator() { //run to lowest position
		// if (limitSwitchStatesString = "001") {
		// 	Stage = 1;
		// }
		
	}

	public void runToLowestPosition() {
		masterMotor.set(ControlMode.PercentOutput, ElevatorConstants.zeroingOutput);
		// return isAtLowestPosition();
	}

	//safety methods
	public boolean isAtLowestPosition() {
		return limitSwitchStatesString.equals(ElevatorConstants.LOWEST_SWITCH_POSITION) || !armBottomSwitch.get();
	}

	public boolean isAtHighestPosition() {
		return limitSwitchStatesString.equals(ElevatorConstants.HIGHEST_SWITCH_POSITION) || !bottomStage1Switch.get();
	}

	public double limitCheck(double input) {
		if (isAtLowestPosition() && input < 0) {
			return 0;
		} else if (isAtHighestPosition() && input > 0) {
			return 0;
		} else {
			return input;
		}
	}

	//get methods
	public double getTargetHeight (int level, MODE currentMode){
	int mode = (currentMode == MODE.CARGO) ? 0 : 1;
	return ElevatorConstants.setElevatorHeight [mode][level - 1];
	}

	public double getHeightInCounts() {
		return masterMotor.getSelectedSensorPosition();
	}

	public double getHeightInCM() {
		return countsToHeight(getHeightInCounts());
	}

	//not useful until now thanks to the brake
	public double getFeedForwardOfStage(int stage) {
		return 1;	
	}

	//math/conversion methods
	public int heightToCounts(double height) {
		return (int) (height * ElevatorConstants.kCountsPerRevSRX / 2 / Math.PI / ElevatorConstants.kEncGearRatio / (ElevatorConstants.kChainWheelDiameter/2));
	}

	public double countsToHeight(double counts) {
		return Utils.countsToRad(counts, ElevatorConstants.kCountsPerRevSRX)*ElevatorConstants.kEncGearRatio*ElevatorConstants.kChainWheelDiameter/2;
	}



  @Override
  public void initDefaultCommand() {
  }
}
