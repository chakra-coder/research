package com.ust.dsms.billing.batch.definition;

import java.util.Collections;
import java.util.List;

public class StepDefinition implements Comparable<StepDefinition> {
    int index;
    private String code;
    private String description;
    
    private String inputSql;
    private List<OutputSql> outputSqls;
    
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getInputSql() {
        return inputSql;
    }

    public void setInputSql(String inputSql) {
        this.inputSql = inputSql;
    }

    public List<OutputSql> getOutputSqls() {
        Collections.sort(outputSqls,(OutputSql o1, OutputSql o2)->o1.getIndex()-o2.getIndex());
        return outputSqls;
    }

    public void setOutputSqls(List<OutputSql> outputSqls) {
        this.outputSqls = outputSqls;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(StepDefinition step) {
        return this.index-step.index;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
