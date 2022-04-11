package ch.pmo.rules;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DisplayName("RuleService")
class RuleRepositoryTest {

    @Test
    @DisplayName("Rules should be read from YAML file")
    void rules_should_be_read_from_yaml_file() {
        var ruleService = new RuleRepository("./test-rules.yaml");
        var rules = ruleService.getAllRules();
        assertThat(rules.isEmpty(), is(false));
    }

    @Test
    @DisplayName("Rules should be empty if YAML file is missing")
    void rules_should_be_empty_if_file_is_missing() {
        var ruleService = new RuleRepository("./missing-file.yaml");
        var rules = ruleService.getAllRules();
        assertThat(rules.isEmpty(), is(true));
    }

}