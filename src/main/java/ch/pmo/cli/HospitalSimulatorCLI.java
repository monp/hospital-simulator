package ch.pmo.cli;

import ch.pmo.cli.converter.DrugConverter;
import ch.pmo.cli.converter.PatientConverter;
import ch.pmo.model.Drug;
import ch.pmo.model.Patient;
import ch.pmo.rules.RuleRepository;
import ch.pmo.rules.RuleEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.*;
import java.util.concurrent.Callable;

import static ch.pmo.cli.PatientStats.count;
import static picocli.CommandLine.ExitCode;

@Command(name = "HospitalSimulator", mixinStandardHelpOptions = true, version = "1.0",
        description = "Hospital Simulator simulate the future patients’ health state, based on their current " +
                "health state and a list of drugs they are administered.")
public final class HospitalSimulatorCLI implements Callable<Integer> {

    private static final Logger logger = LoggerFactory.getLogger(HospitalSimulatorCLI.class);

    @Parameters(index = "0", split = ",", converter = PatientConverter.class, arity = "1",
            description = "List of patients’ health status codes, separated by a comma, e.g. D,F,F")
    private final List<Patient> patients = new ArrayList<>();

    @Parameters(index = "1", split = ",", converter = DrugConverter.class, arity = "0..1",
            description = "List of drug codes, separated by a comma, e.g. As,I")
    private final Set<Drug> drugs = new HashSet<>();

    @Override
    public Integer call() {
        logger.info("Starting simulation with patients: {} and drugs: {}", patients, drugs);

        var ruleService = new RuleRepository();
        var ruleEngine = new RuleEngine(ruleService);

        for (var patient : patients) {
            ruleEngine.eval(patient, Collections.unmodifiableSet(drugs));
        }

        var stats = count(patients);
        logger.info(stats);

        logger.info("End of simulation");

        return ExitCode.OK;
    }

}
