package com.ust.dsms.billing.expression;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ust.dsms.billing.function.BaseFunction;
import com.ust.dsms.billing.rules.service.RuleService;

public class Rule implements Comparable<Rule> {

    private String entity;
    private String description;
    private int index;
    private Expression expression;
    private List<BaseFunction> trueFunctions;
    private List<BaseFunction> falseFunctions;

    public void execute(RuleService ruleService, Map<String, Object> data) throws BillingException {
        if (expression.isTrue(ruleService, data)) {
            for (BaseFunction function : trueFunctions) {
                function.execute(ruleService, data);
            }
        } else if(falseFunctions!=null) {
            for (BaseFunction function : falseFunctions) {
                function.execute(ruleService, data);
            }
        }
    }

    public Rule(String description, int index, Expression expression) {
        super();
        this.description = description;
        this.index = index;
        this.expression = expression;
    }

    public List<BaseFunction> getTrueFunctions() {
    	Collections.sort(trueFunctions,(BaseFunction o1, BaseFunction o2)->o1.getIndex()-o2.getIndex());
        return trueFunctions;
    }

    public void setTrueFunctions(List<BaseFunction> trueFunctions) {
        this.trueFunctions = trueFunctions;
    }

    public List<BaseFunction> getFalseFunctions() {
    	Collections.sort(falseFunctions,(BaseFunction o1, BaseFunction o2)->o1.getIndex()-o2.getIndex());
        return falseFunctions;
    }

    public void setFalseFunctions(List<BaseFunction> falseFunctions) {
        this.falseFunctions = falseFunctions;
    }

    @Override
    public int compareTo(Rule rule) {
        return this.index=rule.index;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public Rule() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "Rule [description=" + description + ", index=" + index + ", expression=" + expression + ", trueFunctions=" + trueFunctions + ", falseFunctions=" + falseFunctions
            + "]";
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }



}
