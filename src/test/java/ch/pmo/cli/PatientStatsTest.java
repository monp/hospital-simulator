package ch.pmo.cli;

import ch.pmo.model.Patient;
import ch.pmo.model.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DisplayName("PatientStats")
class PatientStatsTest {

    @Test
    @DisplayName("the output should be well formatted")
    void test_patient_stats_output() {
        var patients = parse("D,F,X,D,F");
        var result = PatientStats.count(patients);
        assertThat(result, is("F:2,H:0,D:2,T:0,X:1"));
    }

    static List<Patient> parse(String patients) {
        return Stream.of(patients.split(","))
                .map(State::from)
                .map(Patient::new)
                .collect(Collectors.toUnmodifiableList());
    }
}