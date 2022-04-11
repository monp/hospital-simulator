package ch.pmo.model;

import java.util.Arrays;

public enum State {

    FEVER("F"), HEALTHY("H"), DIABETES("D"), TUBERCULOSIS("T"), DEAD("X");

    private final String code;

    State(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static State from(String code) {
        return Arrays.stream(State.values())
                .filter(c -> c.getCode().equals(code))
                .findAny()
                .orElseThrow();
    }

    @Override
    public String toString() {
        return code;
    }
}
