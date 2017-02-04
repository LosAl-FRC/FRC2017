package org.usfirst.frc.team6220.util;

/**
 * Created by student on 2/4/2017.
 */
public class IncrementedRunnable {

    private int count, bound;
    private Runnable runnable;
    private boolean enabled = false;

    public IncrementedRunnable(int bound, Runnable runnable) {
        this.count = 0;
        this.bound = Math.abs(bound);
        this.runnable = runnable;
    }

    public void reset(){
        this.count = 0;
    }

    public void disable(){
        this.enabled = false;
        reset();
    }

    public void enable(){
        this.enabled = true;
        reset();
    }

    public void setRunnable(Runnable runnable){
        this.runnable = runnable;
    }

    public void setBound(int bound){
        reset();
        this.bound = bound;
    }

    public void tick(){
        if(enabled){
            if (count >= bound) {
                reset();
                this.runnable.run();
            }
            count += 1;
        }
    }

}
