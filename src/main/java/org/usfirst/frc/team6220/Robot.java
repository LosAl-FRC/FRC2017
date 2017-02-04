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
import org.usfirst.frc.team6220.subclasses.JoystickControl;
import org.usfirst.frc.team6220.util.IncrementedRunnable;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carter on 1/20/2017.
 */
public class Robot extends SampleRobot {
    //roboRIO-6220-FRC.local
    private ShooterHandler shooterHandler;
    private MecanumDrive driveHandler;
    private IncrementedRunnable printer;
    private Joystick joystick;
    public static final DecimalFormat round = new DecimalFormat("#.000");

    @Override
    public void robotInit() {
        System.out.println("Robot enabled, starting systems");
        this.printer = new IncrementedRunnable(25, () -> System.out.println("Unimplemented"));
        this.joystick = new Joystick(0);
        driveHandler = new MecanumDrive(new RobotDrive(
                new CANTalon(/*frontLeft*/ 1), new CANTalon(/*backLeft*/ 2),
                new CANTalon(/*frontRight*/ 3), new CANTalon(/*backRight*/ 4)
        ));
        shooterHandler = new ShooterHandler(0, new Encoder(0, 1), 2000);
        driveHandler.initialize(this);
        shooterHandler.initialize(this);
        LiveWindow.addActuator("Shooter", "PID", shooterHandler.getPID());
        LiveWindow.addSensor("Shooter", "Encoder", shooterHandler.getEncoder());
    }

    @Override
    public void test(){
        while (isTest() && isEnabled()) {
            LiveWindow.run();
            Timer.delay(0.01);
        }
    }

    @Override
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

    @Override
    public void disabled(){
        System.out.println("Robot disabled, terminating handlers");
        if(shooterHandler != null) shooterHandler.terminate(this);
        if(driveHandler != null) driveHandler.terminate(this);
        if(printer != null) printer.disable();
    }

    public Joystick getJoystick(){
        return joystick;
    }

}

