package org.usfirst.frc.team6220.subclasses;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Created by student on 1/26/2017.
 */
public class RpmPidSource implements PIDSource {

    private final Encoder encoder;

    public RpmPidSource(Encoder encoder, int ticksPerRevolution){
        encoder.setDistancePerPulse(1.0 / ticksPerRevolution);
        this.encoder = encoder;
        this.encoder.setDistancePerPulse(1.0 / ticksPerRevolution);
    }

    public Encoder getEncoder(){
        return this.encoder;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        System.out.println("Nahh mate");
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kRate;
    }

    @Override
    public double pidGet() {
        return this.encoder.getRate() * 60;
    }
}
