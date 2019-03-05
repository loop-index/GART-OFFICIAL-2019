package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class CustomButton extends JoystickButton {

    public CustomButton (GenericHID joystick, int ButtonID){
        super(joystick, ButtonID);
    }

    boolean lastPressed = false;
 
    /**
     * @return Only returns true when the button is pressed the first time
     */
    public boolean uniquePress(){ 
        if (!lastPressed && get()){
            lastPressed = get();
            return true;
        } else {
            lastPressed = get();
            return false;
        }
    }

    /**
     * @return Only returns true when the button is released the first time
     */
    public boolean uniqueRelease(){
        if (lastPressed && !get()){
            lastPressed = get();
            return true;
        } else {
            lastPressed = get();
            return false;
        }
    }
}