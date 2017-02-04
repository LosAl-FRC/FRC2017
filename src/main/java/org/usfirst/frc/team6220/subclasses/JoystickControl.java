package org.usfirst.frc.team6220.subclasses;

import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team6220.Robot;

/**
 * Created by student on 1/21/2017.
 */
public abstract class JoystickControl {

    public static final int
    //These are triggers starting from 0 to 1
            ROTATE_LEFT = 2,
            ROTATE_RIGHT = 3,

    //These are axis starting at 0 going to -1 and 1
            MOVE_LAT = 0, //Latitudinal (left and right)
            MOVE_LONG = 1, //Longitudinal (forward and backward)

    //These are buttons which give off booleans (0 or 1)
            AUTO_CONTINUAL = 0,
            SHOOTER_TOGGLE = 1;

    public static boolean joystickInRange(double lowBound, double upBound, Joystick joystick) {
        return joystick.getRawAxis(0) > lowBound && joystick.getRawAxis(0) < upBound;
    }

    public abstract void initialize(Robot robot);

    public abstract void update(Joystick joystick);

    public abstract void terminate(Robot robot);

}
