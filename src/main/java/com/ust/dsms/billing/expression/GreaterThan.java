package com.ust.dsms.billing.expression;

import com.ust.dsms.billing.relational.operator.RelationalOperator;

public class GreaterThan extends BaseExpression {

    public GreaterThan() {
        super();
    }

    public GreaterThan(String leftOpernad, String rightOpernad) {
        super(leftOpernad, rightOpernad);
    }

    @Override
    public RelationalOperator<Object> getOperator(Object leftOpernadValue) throws BillingException {
        if (leftOpernadValue instanceof Double) {
            return  (Object n1,Object n2) -> (Double)n1>(Double)n2;
        }
        
        if (leftOpernadValue instanceof Long) {
            return  (Object n1,Object n2) -> (Long)n1>(Long)n2;
        }
        
      
        throw new BillingException("GreaterThan : Operator not defined :"+leftOpernadValue);
    }

    @Override
    public String toString() {
        return "GreaterThan [leftOpernad=" + leftOpernad + ", rightOpernad=" + rightOpernad + "]";
    }

}
