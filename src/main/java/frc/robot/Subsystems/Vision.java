
package frc.robot.Subsystems;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Vision extends Subsystem {
  public Thread m_visionThread;
  NetworkTable RPi = NetworkTableInstance.getDefault().getTable("/Raspberry Pi");
	
	NetworkTableEntry xEntry = RPi.getEntry("X");
	NetworkTableEntry yEntry = RPi.getEntry("Y");
  NetworkTableEntry distanceEntry = RPi.getEntry("distance");
  NetworkTableEntry reso = RPi.getEntry("reso");
  NetworkTableEntry mode = RPi.getEntry("mode");

	double width_reso = reso.getDouble(0);
  double distanceOffset = 120.5;
  public boolean arrived = false;
  
  
  public double getCenterTurn(){
		double centerError = xEntry.getDouble(0) - width_reso/2;
		double centerP = 0.125/70;
    return centerError * centerP;
  }

  public double getDistanceSpeed(){
		double distance = distanceEntry.getDouble(0);
		double distanceError = distance - distanceOffset;
		double distanceP = 0.007;

    return distanceError * distanceP;
  }

  public double getCenterX(){
    return xEntry.getDouble(-1);
  }

  public double getCenterError(){
    return xEntry.getDouble(-1) - width_reso/2;
  }

  public double getDistanceToTarget(){
    return distanceEntry.getDouble(0);
  }

  public double getDistanceError(){
    return distanceEntry.getDouble(0) - distanceOffset; 
  }

  public boolean arrivedAtTarget(){
    if (Math.abs(getCenterError()) < ((getDistanceError() > 150) ? 20 : 40)){
      if (getDistanceError() < 50){
        return true;
      }
    }
    return false;
  }

  public String getTrackingMode(){
    return mode.getString("");
  }

  public void toggleMode(){
    if (getTrackingMode().equals("cargo")){
      mode.setString("target");
    } else {
      mode.setString("cargo");
    }
  }
  public void vision(){
    m_visionThread = new Thread (() -> {
    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    camera.setResolution(640, 360);
    camera.setExposureManual(10);
    CvSink cvsink = CameraServer.getInstance().getVideo();
    CvSource outputStream = CameraServer.getInstance().putVideo("vision",640,360);
    Mat mat = new Mat();
    while (!Thread.interrupted()){
      if (cvsink.grabFrame(mat)== 0){
        outputStream.notifyError(cvsink.getError());
        continue;
      }
    }
  });
    }
  @Override
  public void initDefaultCommand() {
  }
}
