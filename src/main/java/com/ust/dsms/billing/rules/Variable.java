package com.ust.dsms.billing.rules;

public class Variable {
    
    private String variableName;
    private VariableType variableType;
    private String variableTemplate;
    
    
    
    public Variable() {
        super();
    }
    public Variable(String variableName, VariableType variableType, String variableTemplate) {
        super();
        this.variableName = variableName;
        this.variableType = variableType;
        this.variableTemplate = variableTemplate;
    }
    public String getVariableName() {
        return variableName;
    }
    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }
    public VariableType getVariableType() {
        return variableType;
    }
    public void setVariableType(VariableType variableType) {
        this.variableType = variableType;
    }
    public String getVariableTemplate() {
        return variableTemplate;
    }
    public void setVariableTemplate(String variableTemplate) {
        this.variableTemplate = variableTemplate;
    }
    
    
    
}
