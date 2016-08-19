package com.ust.dsms.billing.rule.processor;

import com.ust.dsms.billing.expression.BillingException;
import com.ust.dsms.billing.expression.Rule;
import com.ust.dsms.billing.rules.service.RuleService;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class BillingProcessor implements ItemProcessor<Map<String, Object>, Map<String, Object>> {

    private static final Logger log = LoggerFactory.getLogger(BillingProcessor.class);

    @Autowired
    private RuleService ruleService;
    
    @Override
    public Map<String, Object> process(final Map<String, Object> data) throws BillingException {
        
        Set<Rule> rules = ruleService.getRules(data.get("entity"));
        for (Rule rule : rules) {
            rule.execute(ruleService,data);
        }
        return data;

    }
}
