package com.ust.dsms.billing.research;


import com.ust.dsms.billing.expression.AndExpression;
import com.ust.dsms.billing.expression.BaseExpression;
import com.ust.dsms.billing.expression.GreaterThan;
import com.ust.dsms.billing.expression.LessThan;
import com.ust.dsms.billing.expression.Rule;
import com.ust.dsms.billing.function.FunctionAdd;
import com.ust.dsms.billing.function.FunctionSetProratedValue;
import com.ust.dsms.billing.rules.utils.XStreamHelper;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class CreateRule {
    public static void main(String[] args) {
        
        AndExpression and = new AndExpression();
        BaseExpression expression1 = new  GreaterThan("amount", "100");
        BaseExpression expression2 = new LessThan("amount", "1000");
        
        Rule rule = new Rule("Rule 1",1,and);
        rule.setEntity("order");
        rule.getTrueFunctions().add(new FunctionAdd("a","b",1));
        rule.getTrueFunctions().add(new FunctionSetProratedValue("price", "30", "days", "time_prorated_amount"));
        
        and.getExpressions().add(expression1);
        and.getExpressions().add(expression2);
        Set<Rule> rules = new TreeSet<>();
        rules.add(rule);
        
        XStreamHelper helper=new XStreamHelper();
        
        Map<String, Set<Rule>> ruleMap=new HashMap<>();
        
        ruleMap.put("order", rules);
        
        System.out.println(helper.toXml(rules));
        
//        Set<Rule> rule2=(Set<Rule>) helper.fromXml("/home/sajith/workspace/OnTime/dsms-billing/src/main/resources/rules.xml");
//        System.out.println(rule2);
    }    

        
  

}
