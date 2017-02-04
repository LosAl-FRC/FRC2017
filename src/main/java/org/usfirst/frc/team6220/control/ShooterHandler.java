package org.usfirst.frc.team6220.control;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.usfirst.frc.team6220.Robot;
import org.usfirst.frc.team6220.subclasses.JoystickControl;
import org.usfirst.frc.team6220.subclasses.RpmPidSource;
import org.usfirst.frc.team6220.subclasses.Toggleable;

/**
 * Created by student on 1/21/2017.
 */
public class ShooterHandler implements JoystickControl, Toggleable {
    private final PIDController rpmPID;
    private boolean state = false;
    private final VictorSP flywheel;
    private final RpmPidSource rpmPidSource;
    int updateTimer = 0;
    boolean useTimer = false;
    private Encoder encoder;
    private DescriptiveStatistics statistics;
    private long lines = 0;

    public PIDController getPID(){
        return rpmPID;
    }

    public Encoder getEncoder(){
        return encoder;
    }

    public ShooterHandler(int motorChannel, Encoder encoder, int desiredRPM) {
        flywheel = new VictorSP(motorChannel);
        statistics = new DescriptiveStatistics();
        statistics.setWindowSize(30);
        this.encoder = encoder;
        this.rpmPID = new PIDController(
                0.00001, 0, 0,
                rpmPidSource = new RpmPidSource(this.encoder, 12),
                output -> {
                    if(lines >= 25){
                        lines = 0;
                    }
                    lines++;
                    statistics.addValue((int) rpmPidSource.pidGet());
                    flywheel.set((output < 0) ? 0 : output);
                    System.out.println("RPM: " + ((int) rpmPidSource.pidGet())
                            + " OUT: " + Robot.round.format(output)
                            + " SPD: " + Robot.round.format(flywheel.getSpeed())
                            + " AVG: " + (int) statistics.getMean());
                }
        );
        rpmPID.setOutputRange(0, 0.75);
        rpmPID.setSetpoint(desiredRPM);
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
    public void update(Joystick joystick) {
        if (useTimer) {
            updateTimer++;
            if (updateTimer % 50 != 0) {
                return;
            }
            useTimer = false;
        }

        boolean buttonPressed = joystick.getRawButton(JoystickControl.SHOOTER_TOGGLE);
        if (buttonPressed) {
            useTimer = true;
            toggleState();
            if (getState() && !rpmPID.isEnabled()) {
                rpmPID.enable();
                System.out.println("Enabled");
            } else if (!getState() && rpmPID.isEnabled()) {
                rpmPID.disable();
                System.out.println("Disabled");
            }
        }
    }

    @Override
    public void toggleState() {
        Toggleable.super.toggleState();
    }

    @Override
    public boolean getState() {
        return state;
    }

    @Override
    public void setState(boolean state) {
        this.state = state;
    }
}
