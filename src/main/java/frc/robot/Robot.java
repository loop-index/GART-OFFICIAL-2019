
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ArmConstants;
import frc.robot.RobotLoop.StateManager;
import frc.robot.RobotLoop.TeleopLoop;
import frc.robot.RobotLoop.StateManager.WRISTSTATE;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        break;
      case kDefaultAuto:
      default:
        break;
    }
  }
  double targetAngle = 0;
  @Override
  public void teleopInit() {
    StateManager.desiredHeight = 0;
    StateManager.wristState = WRISTSTATE.MOVING;
    targetAngle = 0;
  }

  @Override
  public void teleopPeriodic() {
    //control loop
    // RobotMap.mDrivebase.driveByJoystick();
    // new TeleopLoop();
    RobotMap.mManipulator.updateSensors();

    if (Controls.joystick.getRawButton(3) && !RobotMap.mManipulator.isCargoIn()) {
      RobotMap.mManipulator.cargoIntake();
    } else if (Controls.joystick.getRawButton(2)) {
      RobotMap.mManipulator.cargoFire();
    } else {
      RobotMap.mManipulator.stopCargoWheels();
    }

    if (Controls.joystick.getRawButton(4)) {
      targetAngle = 0;
    } else if (Controls.joystick.getRawButton(1)) {
      targetAngle = ArmConstants.lowerLimit - ArmConstants.angleOffset + 5;
    } else if (Controls.joystick.getRawButton(10)) {
      // targetAngle = ArmConstants.upperLimit - ArmConstants.angleOffset;
      targetAngle = 35;
    }

    RobotMap.mClimber.joystickTest();

    RobotMap.mManipulator.setWristAngle(targetAngle);
    SmartDashboard.putNumber("target", targetAngle);
  }

  @Override
  public void testPeriodic() {
  }
}
