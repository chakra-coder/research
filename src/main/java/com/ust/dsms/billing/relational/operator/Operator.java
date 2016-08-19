package com.ust.dsms.billing.relational.operator;

import com.ust.dsms.billing.rules.service.RuleService;
import java.util.Map;

@FunctionalInterface
public interface Operator<T> {
  void apply(RuleService ruleService,Map<String, Object> data,T v1,T v2);
}
