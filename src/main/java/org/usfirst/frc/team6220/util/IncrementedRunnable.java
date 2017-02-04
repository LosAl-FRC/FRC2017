package org.usfirst.frc.team6220.util;

/**
 * Created by student on 2/4/2017.
 */
public class IncrementedRunnable {

    private int count;
    private Runnable runnable;

    public IncrementedRunnable() {
        this(() -> {
            //EMPTY
        });
    }

    public IncrementedRunnable(Runnable runnable) {
        this.count = 0;
        this.runnable = runnable;
    }

}
