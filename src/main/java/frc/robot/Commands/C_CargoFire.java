package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;
import frc.robot.Constants.ManipulatorConstants;

public class C_CargoFire extends Command {
    boolean isFinished;
    double timeout = ManipulatorConstants.cargoFireTimeout;
    double targetAngle;


    public C_CargoFire() {
        // requires(RobotMap.mManipulator);
    }

    @Override
    protected void initialize() {
        
    }

    protected void execute() {
        
    }
    
    @Override
    protected boolean isFinished() {
        return false;
    }

    protected void end() {

    }

    protected void interrupted() {
        
    }
}
