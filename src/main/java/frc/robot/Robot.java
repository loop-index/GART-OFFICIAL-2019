
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ManipulatorConstants;
import frc.robot.Constants.Utils;
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
  
  @Override
  public void teleopInit() {
    StateManager.targetHeight = 0;
    StateManager.wristState = WRISTSTATE.MOVING;
    StateManager.targetAngle = RobotMap.mManipulator.getWristAngle();
  }

  @Override
  public void teleopPeriodic() {
    //control loop
    // RobotMap.mDrivebase.driveByJoystick();
    // new TeleopLoop();
    TeleopLoop.teleopLoop();
    // RobotMap.mManipulator.getFeedForward();
    SmartDashboard.putNumber("cos angle in rad", Math.cos(Utils.d2r(RobotMap.mManipulator.getWristAngle())));
  }

  @Override
  public void testPeriodic() {
  }
}
