package org.usfirst.frc.team6220.control;

import edu.wpi.first.wpilibj.*;
import org.usfirst.frc.team6220.Robot;
import org.usfirst.frc.team6220.subclasses.JoystickControl;

/**
 * Created by student on 1/21/2017.
 */
public class MecanumDrive extends JoystickControl {

    private final RobotDrive drive;
    private PIDController pidController;

    public MecanumDrive(RobotDrive drive) {
        this.drive = drive;
        this.pidController = new PIDController(0, 0, 0, new ADXRS450_Gyro(), output -> {
            //Nothing yet
        });
        this.pidController.setSetpoint(0);
    }

    public void setAngle(int angle) {
        if (pidController.isEnabled()) {
            disableGyro();
        }
        pidController.setSetpoint(angle);
        enableGyro();
    }

    public void enableGyro() {
        pidController.enable();
    }

    public void disableGyro() {
        pidController.disable();
    }

    @Override
    public void initialize(Robot robot) {
    }

    @Override
    public void terminate(Robot robot) {
    }

    int lastPressed;

    @Override
    public void update(Joystick joystick) {
        if (joystick.getRawButton(MOVE_ANGLE_RIGHT) && lastPressed != MOVE_ANGLE_RIGHT) {
            setAngle(-45);
            lastPressed = MOVE_ANGLE_RIGHT;
        } else if (joystick.getRawButton(MOVE_ANGLE_LEFT) && lastPressed != MOVE_ANGLE_LEFT) {
            setAngle(45);
            lastPressed = MOVE_ANGLE_LEFT;
        } else if (pidController.isEnabled() && !joystick.getRawButton(MOVE_ANGLE_LEFT)
                && !joystick.getRawButton(MOVE_ANGLE_RIGHT)) {
            disableGyro();
            pidController.setSetpoint(0);
        }
        double movementX = joystick.getRawAxis(MOVE_LAT),
                movementY = joystick.getRawAxis(MOVE_LONG),
                rotation;
        if (pidController.isEnabled()) {
            rotation = pidController.get();
        } else {
            rotation = joystick.getRawAxis(ROTATE_RIGHT) - joystick.getRawAxis(ROTATE_LEFT);
        }
        drive.mecanumDrive_Cartesian(movementX, movementY, rotation, 0);
    }
}
