package org.usfirst.frc.team6220;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.hal.EncoderJNI;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import org.usfirst.frc.team6220.control.MecanumDrive;
import org.usfirst.frc.team6220.control.ShooterHandler;
import org.usfirst.frc.team6220.control.TankDrive;
import org.usfirst.frc.team6220.util.IncrementedRunnable;


import java.text.DecimalFormat;

/**
 * Created by Carter on 1/20/2017.
 */
public class Robot extends SampleRobot {
    //roboRIO-6220-FRC.local
    private MecanumDrive driveHandler;
    private IncrementedRunnable printer;
    private ShooterHandler shooterHandler;
    private Joystick joystick;
    public static final DecimalFormat round = new DecimalFormat("#.000");

    public void robotInit() {
        System.out.println("Robot has started");
        this.printer = new IncrementedRunnable(25, () -> System.out.println("Unimplemented"));
        this.driveHandler = new MecanumDrive(new RobotDrive(
                new CANTalon(/*frontLeft*/ 1), new CANTalon(/*backLeft*/ 2),
                new CANTalon(/*frontRight*/ 3), new CANTalon(/*backRight*/ 4)
        ));
        this.shooterHandler = new ShooterHandler(0, new Encoder(0, 1), 2000);
        this.joystick = new Joystick(0);
        LiveWindow.addActuator("Shooter", "PID", shooterHandler.getPID());
        LiveWindow.addSensor("Shooter", "Encoder", shooterHandler.getEncoder());
    }

    public void test(){
        while (isTest() && isEnabled()) {
            shooterHandler.update(joystick);
            LiveWindow.run();
            Timer.delay(0.1);
        }
    }

    public void operatorControl() {
        printer.setRunnable(() -> System.out.println("Shooter: " + shooterHandler.getRPM()));
        printer.setBound(25);
        printer.enable();
        while (isOperatorControl() && isEnabled()) {
            driveHandler.update(joystick);
            shooterHandler.update(joystick);
            printer.tick();
            Timer.delay(0.01);
        }
        printer.disable();
    }

}

