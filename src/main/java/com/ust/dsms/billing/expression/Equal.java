package com.ust.dsms.billing.expression;

import com.ust.dsms.billing.relational.operator.RelationalOperator;

public class Equal extends BaseExpression {

    public Equal() {
        super();
    }

    public Equal(String leftOpernad, String rightOpernad) {
        super(leftOpernad, rightOpernad);
    }

    @Override
    public RelationalOperator<Object> getOperator(Object leftOpernadValue) throws BillingException {

        return (Object o1, Object o2) -> o1.equals(o2);
    }

    @Override
    public String toString() {
        return "Equal [leftOpernad=" + leftOpernad + ", rightOpernad=" + rightOpernad + "]";
    }

    
}
