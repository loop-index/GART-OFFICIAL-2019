package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class LimitSwitch extends DigitalInput {
    int port;
    boolean isNO;
    public LimitSwitch(int port, boolean isNO) {
        super(port);
        this.isNO = isNO;
    }

    public boolean isPressed() {
        return isNO ? !get() : get();
    }
}