
package frc.robot.Constants;

public class ElevatorConst {

    private double[][] elevatorHeight = {{0, 1, 2}, //cargoHeight
                                         {0, 1, 2}}; //hatchHeight

    private double zeroHeight = 0;

    public double getDesiredHeight (int level, boolean cargoMode){
        int mode = (cargoMode) ? 0 : 1;
        return elevatorHeight [mode][level - 1];
    }
}
