package org.usfirst.frc.team6220.control;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import org.usfirst.frc.team6220.subclasses.JoystickControl;

/**
 * Created by student on 1/21/2017.
 */
public class MecanumDrive implements JoystickControl {

    private final RobotDrive drive;

    public MecanumDrive(RobotDrive drive) {
        this.drive = drive;
    }

    @Override
    public void update(Joystick joystick) {
        double rotation = joystick.getRawAxis(ROTATE_RIGHT) - joystick.getRawAxis(ROTATE_LEFT),
                movementX = joystick.getRawAxis(MOVE_LAT),
                movementY = joystick.getRawAxis(MOVE_LONG);
        drive.mecanumDrive_Cartesian(movementX, movementY, rotation, 0);
    }
}
