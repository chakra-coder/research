package com.ust.dsms.billing.relational.operator;

@FunctionalInterface
public interface RelationalOperator<T> {
  boolean apply(T leftValue,T rightValue);
}
