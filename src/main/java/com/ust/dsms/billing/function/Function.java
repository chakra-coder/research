package com.ust.dsms.billing.function;

import com.ust.dsms.billing.rules.service.RuleService;
import java.util.Map;

public interface Function {
    public void execute(RuleService ruleService,Map<String, Object> data);
}
