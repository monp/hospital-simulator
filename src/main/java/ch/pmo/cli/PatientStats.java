package ch.pmo.cli;

import ch.pmo.model.Patient;
import ch.pmo.model.State;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

final class PatientStats {

    private PatientStats() {
        throw new java.lang.UnsupportedOperationException("This class cannot be instantiated");
    }

    public static String count(List<Patient> patients) {
        return Arrays.stream(State.values())
                .map(p -> p + ":" + patients.stream().map(Patient::getState).filter(s -> s.equals(p)).count())
                .collect(Collectors.joining(","));
    }

}
