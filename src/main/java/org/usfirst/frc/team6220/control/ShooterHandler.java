package org.usfirst.frc.team6220.control;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.usfirst.frc.team6220.Robot;
import org.usfirst.frc.team6220.subclasses.JoystickControl;
import org.usfirst.frc.team6220.subclasses.RpmPidSource;
import org.usfirst.frc.team6220.subclasses.Toggleable;
import org.usfirst.frc.team6220.util.IncrementedRunnable;

import static edu.wpi.first.wpilibj.RobotState.isEnabled;

/**
 * Created by student on 1/21/2017.
 */
public class ShooterHandler extends JoystickControl implements Toggleable {
    private final PIDController rpmPID;
    private final RpmPidSource rpmPidSource;
    private final VictorSP flywheel;
    private Encoder encoder;
    private IncrementedRunnable incrementedRunnable;
    private DescriptiveStatistics statistics;

    private boolean doToggle = false, state = false;

    public ShooterHandler(int motorChannel, Encoder encoder, int desiredRPM) {
        statistics = new DescriptiveStatistics();
        statistics.setWindowSize(30);
        flywheel = new VictorSP(motorChannel);
        this.encoder = encoder;
        this.rpmPID = new PIDController(
                0.00001, 0, 0,
                rpmPidSource = new RpmPidSource(this.encoder, 12),
                output -> {
                    statistics.addValue((int) rpmPidSource.pidGet());
                    //flywheel.set((output < 0) ? 0 : output);
                    if(state && isEnabled()){
                        System.out.println("RPM: " + ((int) rpmPidSource.pidGet())
                                + " OUT: " + Robot.round.format(output)
                                + " SPD: " + Robot.round.format(flywheel.getSpeed())
                                + " AVG: " + (int) statistics.getMean());
                    }
                }
        );
        rpmPID.setOutputRange(0, .25);
        rpmPID.setSetpoint(desiredRPM);
    }

    @Override
    public void initialize(Robot robot) {
        this.incrementedRunnable = new IncrementedRunnable(20, new Runnable() {
            private boolean lastButtonPressed = false;
            @Override
            public void run() {
                boolean newButtonPressed = robot.getJoystick().getRawButton(JoystickControl.SHOOTER_TOGGLE);
                if(!lastButtonPressed && newButtonPressed){ //false true
                    lastButtonPressed = true;
                    doToggle = true;
                }else if(lastButtonPressed && !newButtonPressed){ //true false
                    lastButtonPressed = false;
                }
            }
        });
        this.incrementedRunnable.enable();
    }

    @Override
    public void update(Joystick joystick) {
        this.incrementedRunnable.tick();
        if (doToggle) {
            toggleState();
            if (getState() && !rpmPID.isEnabled()) {
                flywheel.set(.25);
                rpmPID.enable();
                System.out.println("Shooter Enabled");
            } else if (!getState() && rpmPID.isEnabled()) {
                rpmPID.disable();
                flywheel.set(0);
                System.out.println("Shooter Disabled");
            }
        }
    }

    @Override
    public void terminate(Robot robot) {
        if(incrementedRunnable != null) incrementedRunnable.disable();
        if(rpmPID != null) rpmPID.disable();
        doToggle = false;
        state = false;
    }

    public String getRPM(){
        return "";//String.valueOf(encoder.getRate() * 60);
                /*"RPM: " + Robot.round.format(rpmPidSource.pidGet())
                + " | Speed: " + Robot.round.format(flywheel.getSpeed())
                + " | Encoder: " + Robot.round.format((rpmPidSource.getEncoder().getRate() * 60))
                + " | RPM=ENCODER: " + (rpmPidSource.pidGet() == rpmPidSource.getEncoder().getRate() * 60);*/

    }

    public void setDesiredRPM(int rpm){
        rpmPID.disable();
        rpmPID.setSetpoint(rpm);
        rpmPID.enable();
    }

    @Override
    public void toggleState() {
        Toggleable.super.toggleState();
        doToggle = false;
    }

    @Override
    public boolean getState() {
        return state;
    }

    @Override
    public void setState(boolean state) {
        this.state = state;
    }

    public PIDController getPID(){
        return rpmPID;
    }

    public Encoder getEncoder(){
        return encoder;
    }

}
