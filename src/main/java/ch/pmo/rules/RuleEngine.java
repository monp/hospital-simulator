package ch.pmo.rules;

import ch.pmo.model.Drug;
import ch.pmo.model.Patient;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class RuleEngine {

    private final RulesEngine rulesEngine = new DefaultRulesEngine();
    private final RuleRepository ruleRepository;

    public RuleEngine(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public void eval(Patient patient, Collection<Drug> drugs) {
        ruleRepository.getAllRules().ifPresent(rules -> {
            var facts = new Facts();
            facts.put("patient", patient);
            facts.put("drugs", names(drugs));
            rulesEngine.fire(rules, facts);
        });
    }

    private List<String> names(Collection<Drug> drugs) {
        return drugs.stream()
                .map(Drug::toString)
                .collect(Collectors.toUnmodifiableList());
    }

}
