package com.ust.dsms.billing.function;

import com.ust.dsms.billing.rules.service.RuleService;
import java.util.Map;

public class FunctionSetProratedValue extends BaseFunction {

    private String valueField;
    private String maxUnitField;
    private String actualUnitField;
    private String variableField;

    @Override
    public void execute(RuleService ruleService, Map<String, Object> data) {
        
        Double value = (Double)ruleService.get(valueField, data);
        Double maxUnit = (Double)ruleService.get(maxUnitField, data);
        int actualUnit = (int)ruleService.get(actualUnitField, data);
        ruleService.lock(variableField, data);
        ruleService.set(variableField, value/maxUnit*actualUnit, data);
    }

    public FunctionSetProratedValue() {
        super();
        // TODO Auto-generated constructor stub
    }

    public FunctionSetProratedValue(int index) {
        super(index);
        // TODO Auto-generated constructor stub
    }

    public FunctionSetProratedValue(String valueField, String maxUnitField, String actualUnitField, String variableField) {
        super();
        this.valueField = valueField;
        this.maxUnitField = maxUnitField;
        this.actualUnitField = actualUnitField;
        this.variableField = variableField;
    }

    public String getValueField() {
        return valueField;
    }

    public void setValueField(String valueField) {
        this.valueField = valueField;
    }

    public String getMaxUnitField() {
        return maxUnitField;
    }

    public void setMaxUnitField(String maxUnitField) {
        this.maxUnitField = maxUnitField;
    }

    public String getActualUnitField() {
        return actualUnitField;
    }

    public void setActualUnitField(String actualUnitField) {
        this.actualUnitField = actualUnitField;
    }

    public String getVariableField() {
        return variableField;
    }

    public void setVariableField(String variableField) {
        this.variableField = variableField;
    }


}
