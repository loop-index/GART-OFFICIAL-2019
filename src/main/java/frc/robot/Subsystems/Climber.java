
package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Controls;
import frc.robot.RobotMap;
import frc.robot.Constants.Utils;

public class Climber extends Subsystem {
  public VictorSP puller = new VictorSP(RobotMap.PULLER);

  public void joystickTest() {
    puller.set(Utils.deadband(Controls.joystick.getRawAxis(1), 0.1) * 0.6);
  }

  @Override
  public void initDefaultCommand() {
  }
}
