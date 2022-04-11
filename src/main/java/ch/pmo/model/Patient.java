package ch.pmo.model;

import java.util.Objects;

public class Patient {

    private State state;

    public Patient(State state) {
        this.state = Objects.requireNonNull(state, "The patient health state cannot be null.");
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return state.getCode();
    }
}
