
package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Controls;
import frc.robot.RobotMap;
import frc.robot.Constants.DriveConst;
import frc.robot.Constants.Utils;

public class Drivebase extends Subsystem implements PIDOutput{

  //---HARDWARE---//

  // WPI_TalonSRX leftMotor = new WPI_TalonSRX(RobotMap.CAN_LEFT_MASTER);
  // WPI_TalonSRX rightMotor = new WPI_TalonSRX(RobotMap.CAN_RIGHT_MASTER);
  // WPI_TalonSRX leftSlave = new WPI_TalonSRX(RobotMap.CAN_LEFT_FOLLOW);
  // WPI_TalonSRX rightSlave = new WPI_TalonSRX(RobotMap.CAN_RIGHT_FOLLOW);

  VictorSP leftMotor = new VictorSP(0);
  VictorSP rightMotor = new VictorSP(1);

  DifferentialDrive mDrive = new DifferentialDrive(leftMotor, rightMotor);

  // AHRS NavX = new AHRS(RobotMap.NavXPort);

  // PIDController pid = new PIDController(0, 0, 0, NavX, this);

  double leftSpeed = 0;
  double rightSpeed = 0;

  //-------------//

  // Initialize values/devices
  public Drivebase(){

    // leftSlave.follow(leftMotor);
    // rightSlave.follow(rightMotor);

    // leftMotor.setInverted(true);
    // leftSlave.setInverted(true);

    // NavX.reset();
  }

  /**
   * Has two modes: 
   * 1. Drive forward/backward using joystick input.
   * 2. Vision-assisted movement, for accurate target navigation. Activates when trigger is held.
   */
  public void driveByJoystick(){

    if (!Controls.getVisionTrigger()){ 
      // leftSpeed = Utils.getMedian(Controls.getLeftJoystick() * 0.5, leftSpeed + DriveConst.maxAcc, leftSpeed - DriveConst.maxAcc);
		  // rightSpeed = Utils.getMedian(Controls.getRightJoystick() * 0.5, rightSpeed + DriveConst.maxAcc, rightSpeed - DriveConst.maxAcc);
      
      // leftMotor.set(leftSpeed);
      // rightMotor.set(rightSpeed);

      driveBySteering();

    } else { 
      driveByVision();
    }

  }

  double driveSpeed = 0;
  double turn = 0;

  /**
   * Drive by steering wheel
   */
  public void driveBySteering(){
    double multiplier = 0.5;

    // if (Controls.BOOSTSPEED.get()){	
    //   multiplier = 0.7;
    // }
    driveSpeed = Utils.getMedian(Controls.getLeftJoystick() * multiplier, driveSpeed + DriveConst.maxAcc, driveSpeed - DriveConst.maxAcc);
    turn = 0.55 * Controls.steering.getRawAxis(0);
  
    mDrive.curvatureDrive(driveSpeed, turn, Controls.steering.getRawButton(5) || Controls.steering.getRawButton(6));

  }

  /**
   * Drives based on coordinates returned by Raspberry Pi
   */
  public void driveByVision() {
		if (RobotMap.mVision.getCenterX() != -1){

			if (Math.abs(RobotMap.mVision.getCenterError()) > ((RobotMap.mVision.getDistanceError() > 150) ? 20 : 40)){
        double turnSpeed = RobotMap.mVision.getCenterTurn();

				leftMotor.set(turnSpeed);
        rightMotor.set(turnSpeed);
        
			} else if (RobotMap.mVision.getDistanceError() > 0){

				leftMotor.set(-DriveConst.visionSpeed);
        rightMotor.set(DriveConst.visionSpeed);
        
			} else {
				stop();
			}
		}
  }

  /**
   * @return returns yaw heading (perpendicular to ground) in degrees
   */
  // public double getCurrentAngle(){
  //   return NavX.getFusedHeading();
  // }

  /**
   * @param angle rotate TO angle, not rotate BY angle
   */
  // public void setupTurnPID(int angle){
  //   pid.setPID(90, 0.013, 0, 0.027);
  //   pid.setInputRange(0, 360);
	// 	pid.setOutputRange(-DriveConst.maxTurnSpeed, DriveConst.maxTurnSpeed);
	// 	pid.setAbsoluteTolerance(2);
	// 	pid.setContinuous(true);
	// 	pid.reset();
	// 	pid.setSetpoint(angle);
  //   pid.enable();
  // }

   /**
   * @param angle rotate TO angle, not rotate BY angle
   */
  // public void turnToAngle(int angle){
	// 	if (!pid.onTarget()){
	// 		leftMotor.set(pid.get());
	// 		rightMotor.set(pid.get());
	// 	} else {
  //     stop();
  //   }
  // }
  // // nghivh
  // public void left(){
  //   leftMotor.set(0.3);
  // }
  // public void right() {
  //   rightMotor.set(0.3);
  // }

  /**
   * Stop both motors
   */
  public void stop(){
    leftMotor.stopMotor();
    rightMotor.stopMotor();
  }

  @Override
  public void initDefaultCommand() {
  }

  @Override
  public void pidWrite(double output) {

  }
}
