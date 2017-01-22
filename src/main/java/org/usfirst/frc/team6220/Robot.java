package org.usfirst.frc.team6220;

import edu.wpi.first.wpilibj.*;


import java.text.DecimalFormat;

/**
 * Created by Carter on 1/20/2017.
 */
public class Robot extends SampleRobot {
    //roboRIO-6220-FRC.local
    private DriveHandler driveHandler;
    private Joystick joystick; //port bottom right on Carter's laptop
    private VictorSP snowblow;

    //Called when the robot is initialized
    public void robotInit() {
        System.out.println("Robot has started");
        driveHandler = new DriveHandler(new RobotDrive(1, 2, 3, 4));
        this.joystick = new Joystick(0);
        this.snowblow = new VictorSP(5);
    }

    DecimalFormat round = new DecimalFormat("#.###");

    public void operatorControl() {
        int i = 0;
        while (isOperatorControl() && isEnabled()) {
            double rotation = joystick.getRawAxis(3) - joystick.getRawAxis(2),
                    movementX = joystick.getRawAxis(0),
                    movementY = joystick.getRawAxis(1);
            drive.mecanumDrive_Cartesian(movementX, movementY, rotation, 0);

            if (i % 50 == 0) {
                i = 0;
                System.out.println(
                        "Motor Out: " + round.format(snowblow.get())
                                + " | Joystick: " + String.valueOf(joystick.getRawAxis(0)));
            }
            i++;
            Timer.delay(0.01);
        }
    }

    public boolean joystickInRange(double lowBound, double upBound) {
        return joystick.getRawAxis(0) > lowBound && joystick.getRawAxis(0) < upBound;
    }

    /*
    public void operatorControl() {
        int i = 0;
        while (isOperatorControl() && isEnabled()) {
            double movement = joystick.getRawAxis(3) - joystick.getRawAxis(2);
            drive.drive(movement * (flip ? -1 : 1), 0);
            double angle = (joystick.getRawAxis(0) + 1) / 2;
            if(angle > .49 && angle < .505){
                angle = .5;
            }
            boolean reverseFlip = flip;
            if(movement < 0){
                reverseFlip = !flip;
            }
            double wheelPosition = 90;
            wheelPosition = 90 + (((angle - .5) * (reverseFlip ? 1 : -1)) * 90);
            //wheelPosition = 180 * angle;
            //angle = Math.pow(angle, joystick.getRawAxis(3) - joystick.getRawAxis(2));
            steering.setAngle(wheelPosition);
            //drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
            //drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
            if(i % 50 == 0){
                i = 0;
                System.out.println("Movement: " + round.format(movement)
                        + " | Wheel Position: " + round.format(wheelPosition)
                        + " | Angle: " + round.format(angle)
                        + " | Actual Position: " + round.format(steering.getPosition()));
                //System.out.println("Raw Angle: " + steering.get() + " Raw Angle: " + steering.getAngle() + " Speed: " + steering.getSpeed());
            }
            i++;
            Timer.delay(0.01);
        }
    }*/


}

