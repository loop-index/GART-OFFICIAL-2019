
package frc.robot;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
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
      // UsbCamera camera = new UsbCamera("", 0);
      // camera.setResolution(640, 360);
      
      // CameraServer.getInstance().startAutomaticCapture();
    
      // CvSink cvsink = CameraServer.getInstance().getVideo();
      // CvSource outputStream = CameraServer.getInstance().putVideo("name", 640, 360);
      //   Mat source = new Mat();
      //   while(true){
      //     cvsink.grabFrame(source);
      //     outputStream.putFrame(source);
      //   }
      // }).start();
    
  
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  
    RobotMap.mElevator.zero();
    StateManager.level = 1;
    StateManager.targetHeight = 0;
    StateManager.targetAngle = RobotMap.mManipulator.getWristAngle();
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
    // switch (m_autoSelected) {
    //   case kCustomAuto:
    //     break;
    //   case kDefaultAuto:
    //   default:
    //     break;
    // }
    TeleopLoop.teleopLoop();
  }
  
  @Override
  public void teleopInit() {
    // TeleopLoop.teleopLoop();
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
