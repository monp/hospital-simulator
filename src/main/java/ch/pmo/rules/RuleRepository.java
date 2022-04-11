package ch.pmo.rules;

import ch.pmo.model.State;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.reader.YamlRuleDefinitionReader;
import org.mvel2.ParserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.util.Optional;

public class RuleRepository {

    private static final Logger logger = LoggerFactory.getLogger(RuleRepository.class);

    private static final String DEFAULT_RULES_FILENAME = "rules.yaml";

    private final String filename;

    private Rules rules;

    public RuleRepository() {
        this(DEFAULT_RULES_FILENAME);
    }

    public RuleRepository(String filename) {
        this.filename = filename;
    }

    public Optional<Rules> getAllRules() {
        if (rules == null) {
            loadRules();
        }
        return Optional.ofNullable(rules);
    }

    public void loadRules() {
        try {
            var parserContext = new ParserContext();
            parserContext.addImport(State.class);

            var ruleFactory = new MVELRuleFactory(new YamlRuleDefinitionReader(), parserContext);
            var inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
            if (inputStream != null) {
                rules = ruleFactory.createRules(new InputStreamReader(inputStream));
            }
        }
        catch (Exception e) {
            logger.error("cannot load rules from file.", e);
        }
    }
}
