package com.ust.dsms.billing.function;

import com.ust.dsms.billing.rules.service.RuleService;
import java.util.Map;

public abstract class BaseFunction implements Comparable<BaseFunction>{
  
  private int index;
  
  public abstract void execute(RuleService ruleService,Map<String, Object> data);
  
  public BaseFunction() {
    super();
  }

  public BaseFunction(int index) {
    super();
    this.index = index;
  }

  public int getIndex() {
    return index;
  }
  public void setIndex(int index) {
    this.index = index;
  }

  @Override
  public int compareTo(BaseFunction function) {
    return this.index-function.index;
  }
}
