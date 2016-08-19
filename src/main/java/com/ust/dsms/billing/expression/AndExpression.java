package com.ust.dsms.billing.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ust.dsms.billing.rules.service.RuleService;

public class AndExpression implements Expression {

  private List<Expression> expressions;

  public AndExpression() {
    super();
  }

  @Override
  public boolean isTrue(RuleService ruleService, Map<String, Object> data) throws BillingException {
    for (Expression expression : expressions) {
      if (!expression.isTrue(ruleService, data)) {
        return false;
      }
    }
    return true;
  }

  public List<Expression> getExpressions() {
    if (expressions == null) {
      expressions = new ArrayList<>();
    }
    return expressions;
  }

  public void setExpressions(List<Expression> expressions) {
    this.expressions = expressions;
  }

  @Override
  public String toString() {
    return "AndExpression [expressions=" + expressions + "]";
  }
  
}
