package org.usfirst.frc.team6220;

import edu.wpi.first.wpilibj.*;

import java.text.DecimalFormat;

/**
 * Created by Carter on 1/20/2017.
 */
public class Robot extends SampleRobot{
    //roboRIO-6220-FRC.local
    private RobotDrive drive; //pins 0,1,2,3
    private Joystick joystick; //port bottom right on Carter's laptop
    //private Servo steering; //pin 4
    private Encoder encoder;
    private VictorSP snowblow;

    //Called when the robot is initialized
    public void robotInit() {
        System.out.println("Robot has started");
        this.drive = new RobotDrive(0, 1, 2, 3);
        this.joystick = new Joystick(0);
        //this.steering = new Servo(4);
        this.encoder = new Encoder(0, 1);
        this.snowblow = new VictorSP(5);
    }

    boolean flip = false;
    DecimalFormat round = new DecimalFormat("#.###");
    int encoderCount = 0, upperLimit = 20, lowerLimit = -20;

    public void operatorControl() {
        int i = 0;
        while (isOperatorControl() && isEnabled()) {
            encoderCount = encoder.get();
            if(encoderCount >= upperLimit && joystick.getRawAxis(0) > 0){
                snowblow.set(0);
            }else if(joystick.getRawAxis(0) > 0){
                snowblow.set(.3);
            }else if(encoderCount <= lowerLimit && joystick.getRawAxis(0) < 0){
                snowblow.set(0);
            }else if(joystick.getRawAxis(0) < 0){
                snowblow.set(-0.3);
            }

            if(joystick.getRawButton(1)){
                if(encoderCount < -1){
                    snowblow.set(.3);
                }else if(encoderCount > 1){
                    snowblow.set(-.3);
                }else{
                    snowblow.set(0);
                }
            }

            if(joystickInRange(-0.05, 0.05) && !joystick.getRawButton(1)){
                snowblow.set(0);
            }

            double movement = joystick.getRawAxis(3) - joystick.getRawAxis(2);
            drive.drive(movement * (flip ? -1 : 1), 0);

            if(i % 50 == 0){
                i = 0;
                System.out.println(
                        "Motor Out: " + round.format(snowblow.get())
                                + " | Joystick: " + String.valueOf(joystick.getRawAxis(0))
                                + " | Encoder Count: " + encoderCount);
                //System.out.println("Raw Angle: " + steering.get() + " Raw Angle: " + steering.getAngle() + " Speed: " + steering.getSpeed());
            }
            i++;
            Timer.delay(0.01);
        }
    }

    public boolean joystickInRange(double lowBound, double upBound){
        return joystick.getRawAxis(0) > lowBound && joystick.getRawAxis(0) < upBound;
    }

    /*public void operatorControl() {
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

