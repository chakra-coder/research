package com.ust.dsms.billing.rules.service;

import com.ust.dsms.billing.expression.Rule;
import com.ust.dsms.billing.rules.utils.XStreamHelper;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RuleService {

    private static final Logger log = LoggerFactory.getLogger(RuleService.class);

    private static final String FORMAT_DOUBLE = "[+-]*([0-9]*[.])?[0-9]+";
    private static final String FORMAT_LONG = "[+-]*([0-9]*)+";

    private Map<String,Set<Rule>> rules=new HashMap<>();

    @Autowired
    private XStreamHelper xStreamHelper;

    @Autowired
    private VariableService variableService;


    @PostConstruct
    public void initialize() {

        InputStreamReader reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("rules.xml"));
        Set<Rule> ruleSet = (Set<Rule>) xStreamHelper.fromXml(reader);
        for (Rule rule : ruleSet) {
            if(this.rules.get(rule.getEntity())==null){
                this.rules.put(rule.getEntity(), new TreeSet<>());   
            }
            this.rules.get(rule.getEntity()).add(rule);
        }

        log.info("Rules loaded");

    }

    public Object get(String variableName, Map<String, Object> data) {

        if (isDouble(variableName)) {
            return Double.valueOf(variableName);
        }

        if (isLong(variableName)) {
            return Long.valueOf(variableName);
        }

        if (data.containsKey(variableName)) {
            return data.get(variableName);
        }

        Object value = variableService.get(variableName, data);
        return value == null ? variableName : value;

    }

    private boolean isDouble(String value) {
        return value.matches(FORMAT_DOUBLE);
    }

    private boolean isLong(String value) {
        return value.matches(FORMAT_LONG);
    }

    public void set(String variableName, Object value, Map<String, Object> data) {
        data.put(variableName, value);
        variableService.set(variableName, value, data);
    }

    public boolean variableExist(String variableName, Map<String, Object> data) {
        return variableService.exist(variableName,data);
    }

    public void lock(String variableName, Map<String, Object> data) {
        variableService.lock(variableName, data);
    }

    public Set<Rule> getRules(Object entity) {
        if(this.rules.get(entity)!=null){
            return this.rules.get(entity);
        }
        return Collections.EMPTY_SET;
    }

}
