package com.ust.dsms.billing.rules.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.ust.dsms.billing.rules.Variable;
import com.ust.dsms.billing.rules.VariableType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

/**
 * Helper access variables in hazelcast
 * @author u30503
 *
 */
@Component
public class VariableService {

    private static final String VARIABLE_MAP = "variableMap";
    private Map<String, Variable> variableMap;

    @Autowired
    @Qualifier("pgJdbcTemplate")
    private JdbcTemplate pgJdbcTemplate;

    @Autowired
    private HazelcastInstance hazelcastInstance;

    /**
     * Lock and return the value for given variable
     * 
     * @param variableName
     * @param data
     * @return
     */
    public <V> V lock(String variableName, Map<String, Object> data) {
        String variableKey = getKey(variableName, data);
        IMap<String, V> map = hazelcastInstance.getMap(VARIABLE_MAP);
        map.lock(variableKey);
        return map.get(variableKey);
    }

    /**
     * Set and unlock the variable
     * 
     * @param variableName
     * @param value
     * @param data
     */
    public <V> void set(String variableName, V value, Map<String, Object> data) {
        String variableKey = getKey(variableName, data);
        IMap<String, V> map = hazelcastInstance.getMap(VARIABLE_MAP);
        map.set(variableKey, value);
        map.unlock(variableKey);
    }
    
    /**
     * Put the variable
     * 
     * @param variableName
     * @param value
     * @param data
     */
    public <V> void put(String variableName, V value, Map<String, Object> data) {
        String variableKey = getKey(variableName, data);
        IMap<String, V> map = hazelcastInstance.getMap(VARIABLE_MAP);
        map.put(variableKey, value);
    }
    

    /**
     * Return variable value without locking
     * 
     * @param variableName
     * @param data
     * @return
     */
    public <V> V get(String variableName, Map<String, Object> data) {
        if (!variableMap.containsKey(variableName)) {
            return null;
        }
        String variableKey = getKey(variableName, data);
        IMap<String, V> map = hazelcastInstance.getMap(VARIABLE_MAP);
        return map.get(variableKey);
    }
    

    /**
     * Return all variable values for give data
     * 
     * @param data
     * @return
     */
    public Map<String, Object> getAllVariables( Map<String, Object> data) {
        Map<String , Object> variables=new HashMap<>();
        for (String variableName : variableMap.keySet()) {
            variables.put(variableName, hazelcastInstance.getMap(VARIABLE_MAP).get(getKey(variableName, data)));
        }
        
        return variables;
    }


    private String getKey(String variableName, Map<String, Object> data) {
        if (data==null || !variableMap.containsKey(variableName)) {
            return variableName;
        }
        String variableTemplate = variableMap.get(variableName).getVariableTemplate();
        StrSubstitutor sub = new StrSubstitutor(data);
        return sub.replace(variableTemplate);
    }

    @PostConstruct
    public void initialize() {

        ResultSetExtractor<Map<String, Variable>> resultSetExtractor = new ResultSetExtractor<Map<String, Variable>>() {

            @Override
            public Map<String, Variable> extractData(ResultSet rs) throws SQLException {
                Map<String, Variable> map = new HashMap<>();
                while (rs.next()) {
                    Variable variable = new Variable();
                    variable.setVariableName(rs.getString("name"));
                    variable.setVariableType(VariableType.valueOf(rs.getString("type")));
                    variable.setVariableTemplate(rs.getString("template"));
                    map.put(variable.getVariableName(), variable);

                }
                return map;
            }
        };

        this.variableMap = pgJdbcTemplate.query("select * from variable", resultSetExtractor);
    }

    public boolean exist(String variableName, Map<String, Object> data) {
        return hazelcastInstance.getMap(VARIABLE_MAP).keySet().contains(getKey(variableName, data));
    }

    public IMap<String, Object> getMap(String name) {
        return hazelcastInstance.getMap(name);
    }

    public void shutdown() {
        hazelcastInstance.shutdown();        
    }
}
