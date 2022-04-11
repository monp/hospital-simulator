package ch.pmo.cli;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOutNormalized;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("HospitalSimulatorCLI")
class HospitalSimulatorCLITest {

    @Test
    @DisplayName("should work with valid inputs")
    void should_work_with_valid_inputs() throws Exception {
        var errText = tapSystemErr(() -> {
            var outText = tapSystemOutNormalized(() -> new CommandLine(new HospitalSimulatorCLI()).execute("T,F,D", "P"));
            assertThat(outText, containsString("F:0,H:1,D:0,T:1,X:1"));
        });
        assertThat(errText, is(emptyString()));
    }

    @Test
    @DisplayName("should work without drugs")
    void should_work_without_drugs() throws Exception {
        var errText = tapSystemErr(() -> new CommandLine(new HospitalSimulatorCLI()).execute("T,F,D"));
        assertThat(errText, is(emptyString()));
    }

    @Test
    @DisplayName("shouldn't work with invalid inputs")
    void should_not_work_with_invalid_inputs() throws Exception {
        var errText = tapSystemErr(() -> new CommandLine(new HospitalSimulatorCLI()).execute("T,F,D,3,Q"));
        assertThat(errText, containsString("cannot convert '3' to Patient"));
    }
}