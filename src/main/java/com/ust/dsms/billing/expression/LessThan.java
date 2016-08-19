package com.ust.dsms.billing.expression;

import com.ust.dsms.billing.relational.operator.RelationalOperator;

public class LessThan extends BaseExpression {

    public LessThan() {
        super();
    }

    public LessThan(String leftOpernad, String rightOpernad) {
        super(leftOpernad, rightOpernad);
    }

    @Override
    public RelationalOperator<Object> getOperator(Object leftOpernadValue) throws BillingException {

        if (leftOpernadValue instanceof Double) {
            return (Object n1, Object n2) -> (Double) n1 < (Double) n2;
        }

        if (leftOpernadValue instanceof Long) {
            return (Object n1, Object n2) -> (Long) n1 < (Long) n2;
        }

        throw new BillingException("LessThan : Operator not defined :" + leftOpernadValue);
    }

    @Override
    public String toString() {
        return "LessThan [leftOpernad=" + leftOpernad + ", rightOpernad=" + rightOpernad + "]";
    }

}
