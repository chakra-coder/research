package com.ust.dsms.billing.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ust.dsms.billing.rules.service.RuleService;

public class OrExpression implements Expression {

  private List<Expression> expressions;

  public OrExpression() {
    super();
  }

  @Override
  public boolean isTrue(RuleService ruleService,Map<String, Object> data) throws BillingException {
    for (Expression expression : expressions) {
      if(expression.isTrue(ruleService,data)){
        return true;
      }
    }
    return false;
  }

  public List<Expression> getExpressions() {
    if(expressions==null){
      expressions=new ArrayList<>();
    }
    return expressions;
  }

  @Override
  public String toString() {
    return "OrExpression [expressions=" + expressions + "]";
  }

  public void setExpressions(List<Expression> expressions) {
    this.expressions = expressions;
  }
}
