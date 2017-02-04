package org.usfirst.frc.team6220.control;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import org.usfirst.frc.team6220.Robot;
import org.usfirst.frc.team6220.subclasses.JoystickControl;

/**
 * Created by student on 1/26/2017.
 */
public class TankDrive extends JoystickControl {

    private final RobotDrive drive;

    public TankDrive(RobotDrive drive) {
        this.drive = drive;
    }

    @Override
    public void initialize(Robot robot) {

    }

    @Override
    public void terminate(Robot robot) {

    }

    @Override
    public void update(Joystick joystick) {
        double movement = joystick.getRawAxis(3) - joystick.getRawAxis(2);
        drive.drive(movement, 0);
    }
}
