
package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controls {
    public static Joystick steering = new Joystick(0);
    public static Joystick joystick = new Joystick(1);
    public static Joystick control_panel = new Joystick(2);
    public static Joystick testGamepad = new Joystick(3);

    //CONTROL PANEL BUTTONS
    public static CustomButton ABORT = new CustomButton(control_panel, 1);
    public static CustomButton FIRE = new CustomButton(control_panel, 3);
    public static CustomButton ENDGAME = new CustomButton(control_panel, 2);

    public static CustomButton CARGO_MODE = new CustomButton(control_panel, 8);
    public static CustomButton HATCH_MODE = new CustomButton(control_panel, 9);

    public static CustomButton LEVEL_1 = new CustomButton(control_panel, 4);
    public static CustomButton LEVEL_2 = new CustomButton(control_panel, 5);
    public static CustomButton LEVEL_3 = new CustomButton(control_panel, 6);
    public static CustomButton CARGOSHIP = new CustomButton(control_panel, 11); //

    public static CustomButton CARGO_GROUND = new CustomButton(control_panel, 12);
    public static CustomButton HATCH_STATION = new CustomButton(control_panel, 13); //
    public static CustomButton INTAKE = new CustomButton(control_panel, 10);
    public static CustomButton MANUAL_ELEVATOR_TRIG = new CustomButton(control_panel, 7);
    public static int MANUAL_JOYSTICK = 0;

    public static CustomButton BOOSTSPEED = new CustomButton(joystick,1 );
    public static CustomButton NO_ACC_LIM = new CustomButton(joystick, 5);
    public static CustomButton TRACKING_MODE = new CustomButton(joystick, 2); //

    //DRIVE JOYSTICK BUTTONS
    // public static CustomButton BOOSTSPEED = new CustomButton(joystick, 1);
    public static CustomButton TEST = new CustomButton(control_panel, 3);
    public static CustomButton HATCH_TEST = new CustomButton(control_panel, 9);

    public static double getBoostButton(){
        return joystick.getRawAxis(2);
    }

    //CONTROL METHODS
    public static boolean getVisionTrigger(){
        int visionTrigger = 2;
        // return joystick.getRawAxis(visionTrigger) > 0.5;
        return joystick.getRawButton(visionTrigger);
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

