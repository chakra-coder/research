package com.ust.dsms.billing.expression;

import java.util.Map;

import com.ust.dsms.billing.rules.service.RuleService;

public interface Expression {
    boolean isTrue(RuleService ruleService, Map<String, Object> data) throws BillingException;
}
