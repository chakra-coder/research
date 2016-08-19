package com.ust.dsms.billing.batch.definition;

import java.util.Collections;
import java.util.List;

public class JobDefinition {
    
    private String code;
    private String description;
    private List<StepDefinition> steps;

    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<StepDefinition> getSteps() {
    	Collections.sort(steps,(StepDefinition o1, StepDefinition o2)->o1.getIndex()-o2.getIndex());
        return steps;
    }

    public void setSteps(List<StepDefinition> steps) {
        this.steps = steps;
    }
}
