package frc.robot.Subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Constants.DriveConst;

public class ShuffleboardInputs extends Subsystem {
	private static ShuffleboardTab tab = Shuffleboard.getTab("Drive");
	private static NetworkTableEntry normalSpeed =	tab.add("Normal Speed", 0.5).getEntry();
	private static NetworkTableEntry maxSpeed =	tab.add("Max Speed", 0.7).getEntry();
	private static NetworkTableEntry maxAcc =	tab.add("Max Acc", 0.01).getEntry();
	private static NetworkTableEntry turnSensitivity =	tab.add("Turn Sensitivity", 0.7).getEntry();

	// is called by testPeriodic since we don't actualy need to change this live
	// ...righttttttttttttttt?
	public static void updateConst(){
		DriveConst.normalSpeed = normalSpeed.getDouble(0.5);
		DriveConst.maxSpeed = maxSpeed.getDouble(0.7);
		DriveConst.maxAcc = maxAcc.getDouble(0.01);
		DriveConst.turnSensitivity = turnSensitivity.getDouble(0.7);
	}
	@Override
	protected void initDefaultCommand() {
	}
}