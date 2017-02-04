package org.usfirst.frc.team6220.subclasses;

/**
 * Created by student on 1/26/2017.
 */
public interface Toggleable {

    default void toggleState() {
        setState(!getState());
    }

    boolean getState();

    void setState(boolean state);
}
