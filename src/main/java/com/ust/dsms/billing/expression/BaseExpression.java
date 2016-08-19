package com.ust.dsms.billing.expression;

import java.util.Map;

import com.ust.dsms.billing.relational.operator.RelationalOperator;
import com.ust.dsms.billing.rules.service.RuleService;

public abstract class BaseExpression implements Expression {

  protected String leftOpernad;
  protected String rightOpernad;
  
  public BaseExpression() {
    super();
  }

  public BaseExpression(String leftOpernad, String rightOpernad) {
    super();
    this.leftOpernad = leftOpernad;
    this.rightOpernad = rightOpernad;
  }

  public String getLeftOpernad() {
    return leftOpernad;
  }

  public void setLeftOpernad(String leftOpernad) {
    this.leftOpernad = leftOpernad;
  }

  public String getRightOpernad() {
    return rightOpernad;
  }

  public void setRightOpernad(String rightOpernad) {
    this.rightOpernad = rightOpernad;
  }

  @Override
  public boolean isTrue(RuleService ruleService, Map<String, Object> data) throws BillingException {
    
    Object leftOpernadValue=ruleService.get(leftOpernad, data);
    Object rightOpernadValue=ruleService.get(rightOpernad, data);
    

    return getOperator(leftOpernadValue).apply(leftOpernadValue, rightOpernadValue);
  }

  public abstract RelationalOperator<Object> getOperator(Object leftOpernadValue) throws BillingException;


  @Override
  public String toString() {
    return "BaseExpression [leftOpernad=" + leftOpernad + ", rightOpernad=" + rightOpernad + "]";
  }
}
