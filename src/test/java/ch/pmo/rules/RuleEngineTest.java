package ch.pmo.rules;

import ch.pmo.model.Drug;
import ch.pmo.model.Patient;
import ch.pmo.model.State;
import org.hamcrest.Matcher;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.mvel.MVELRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mvel2.ParserContext;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ch.pmo.model.State.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.oneOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("RuleEngine")
class RuleEngineTest {

    Rules defaultRules = new Rules(
        createRule("Aspirin cures Fever", "patient.state == State.FEVER && drugs.contains('As')", "patient.state = State.HEALTHY"),
        createRule("Antibiotic cures Tuberculosis", "patient.state == State.TUBERCULOSIS && drugs.contains('An')", "patient.state = State.HEALTHY"),
        createRule("Insulin prevents diabetic subject from dying, does not cure Diabetes", "patient.state == State.DIABETES && !drugs.contains('I')", "patient.state = State.DEAD"),
        createRule("Insulin mixed with Antibiotic causes Healthy people to catch Fever", "drugs.contains('I') && drugs.contains('An') && patient.state == State.HEALTHY", "patient.state = State.FEVER"),
        createRule("Paracetamol cures Fever", "patient.state == State.FEVER && drugs.contains('P')", "patient.state = State.HEALTHY"),
        createRule("Paracetamol kills subjects if mixed with Aspirin", "drugs.contains('P') && drugs.contains('As')", "patient.state = State.DEAD"),
        createRule("One time in a million the Flying Spaghetti Monster shows his noodly power and resurrects a Dead patient, the patient becomes Healthy", "patient.state == State.DEAD && new java.util.Random().nextInt(1000000) + 1 == 1", "patient.state = State.HEALTHY")
    );

    RuleEngine ruleEngine;

    @BeforeEach
    void setUp() {
        var ruleService = mock(RuleRepository.class);
        when(ruleService.getAllRules()).thenReturn(Optional.of(defaultRules));
        ruleEngine = new RuleEngine(ruleService);
    }

    Rule createRule(String name, String when, String then) {
        var parserContext = new ParserContext();
        parserContext.addImport(State.class);
        return new MVELRule(parserContext).name(name).when(when).then(then);
    }

    @ParameterizedTest(name = "ParameterizedTest {index} {3}")
    @MethodSource("getParameters")
    void test_rules(State given, String drugs, Matcher<State> expected, String ignoredDescription) {
        var patient = new Patient(given);
        ruleEngine.eval(patient, parse(drugs));
        assertThat(patient.getState(), expected);
    }

    static Collection<Drug> parse(String drugs) {
        return Stream.of(drugs.split(","))
                .map(Drug::new)
                .collect(Collectors.toUnmodifiableList());
    }

    static Stream<Arguments> getParameters() {
        return Stream.of(
                Arguments.of(FEVER, "As", is(HEALTHY), "Aspirin cures patient with Fever"),
                Arguments.of(DIABETES, "As", is(DEAD), "Patient with Diabetes will die without Insulin"),
                Arguments.of(DIABETES, "I", is(DIABETES), "Insulin prevents death for patient with Diabetes"),
                Arguments.of(TUBERCULOSIS, "An", is(HEALTHY), "Antibiotic cures Tuberculosis"),
                Arguments.of(HEALTHY, "An,I", is(FEVER), "Insulin mixed with Antibiotic causes Healthy people to catch Fever"),
                Arguments.of(FEVER, "P", is(HEALTHY), "Paracetamol cures Fever"),
                Arguments.of(FEVER, "P,As", is(DEAD), "Paracetamol kills subjects if mixed with Aspirin"),
                Arguments.of(DEAD, "P", oneOf(DEAD, HEALTHY), "Flying Spaghetti Monster resurrects Dead patients sometimes...")
        );
    }
}