package org.usfirst.frc.team6220;

import edu.wpi.first.wpilibj.VictorSP;

/**
 * Created by Al on 1/21/2017.
 */
public class ShooterHandler {
    private boolean activated = false;
    private VictorSP flywheel;

    ShooterHandler(int channel) {
        flywheel = new VictorSP(channel);
    }

    public void toggleWheel() {
        activated = !activated;
    }
}
