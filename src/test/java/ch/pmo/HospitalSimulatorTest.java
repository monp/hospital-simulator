package ch.pmo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import picocli.CommandLine.ExitCode;

import static com.github.stefanbirkner.systemlambda.SystemLambda.catchSystemExit;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("HospitalSimulator")
class HospitalSimulatorTest {

    @Test
    @DisplayName("should return a command line usage error exit code")
    void usage_error_exit_code() throws Exception {
        int exitCode = catchSystemExit(() -> HospitalSimulator.main("3", "P"));
        assertEquals(ExitCode.USAGE, exitCode);
    }

    @Test
    @DisplayName("should return a successful termination exit code")
    void successful_termination_exit_code() throws Exception {
        int exitCode = catchSystemExit(() -> HospitalSimulator.main("F,H", "An"));
        assertEquals(ExitCode.OK, exitCode);
    }

}