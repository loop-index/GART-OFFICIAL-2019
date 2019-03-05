
package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controls {
    public static Joystick steering = new Joystick(0);
    public static Joystick joystick = new Joystick(1);
    public static Joystick control_panel = new Joystick(2);

    //CONTROL PANEL BUTTONS
    public static CustomButton ABORT = new CustomButton(control_panel, 0);
    public static CustomButton FIRE = new CustomButton(control_panel, 0);
    public static CustomButton ENDGAME = new CustomButton(control_panel, 0);

    public static CustomButton CARGO_MODE = new CustomButton(control_panel, 0);
    public static CustomButton HATCH_MODE = new CustomButton(control_panel, 0);

    public static CustomButton LEVEL_1 = new CustomButton(control_panel, 0);
    public static CustomButton LEVEL_2 = new CustomButton(control_panel, 0);
    public static CustomButton LEVEL_3 = new CustomButton(control_panel, 0);

    public static CustomButton CARGO_STATION = new CustomButton(control_panel, 0);
    public static CustomButton CARGO_GROUND = new CustomButton(control_panel, 0);
    public static CustomButton HATCH_STATION = new CustomButton(control_panel, 0);
    public static CustomButton ROTATE = new CustomButton(control_panel, 0); //?

    //DRIVE JOYSTICK BUTTONS

    //CONTROL METHODS
    public static boolean getVisionTrigger(){
        int visionTrigger = 2;
        return joystick.getRawAxis(visionTrigger) > 0.5;
    }

    public static double getLeftJoystick(){
        return joystick.getRawAxis(1);
    }

    public static double getRightJoystick(){
        return joystick.getRawAxis(5);
    }

    public static boolean floorIntakeTrigger(){
        return CARGO_GROUND.uniquePress();
    }
}

