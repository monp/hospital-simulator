package ch.pmo;

import ch.pmo.cli.HospitalSimulatorCLI;
import picocli.CommandLine;

public final class HospitalSimulator {

    public static void main(String... args) {
        int exitCode = new CommandLine(new HospitalSimulatorCLI()).execute(args);
        System.exit(exitCode);
    }

}
