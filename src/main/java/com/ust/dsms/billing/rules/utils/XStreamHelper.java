package com.ust.dsms.billing.rules.utils;

import java.io.File;
import java.io.InputStreamReader;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;
import com.ust.dsms.billing.batch.definition.JobDefinition;
import com.ust.dsms.billing.batch.definition.OutputSql;
import com.ust.dsms.billing.batch.definition.StepDefinition;
import com.ust.dsms.billing.expression.AndExpression;
import com.ust.dsms.billing.expression.BaseExpression;
import com.ust.dsms.billing.expression.GreaterThan;
import com.ust.dsms.billing.expression.LessThan;
import com.ust.dsms.billing.expression.OrExpression;
import com.ust.dsms.billing.expression.Rule;
import com.ust.dsms.billing.function.FunctionAdd;
import com.ust.dsms.billing.function.FunctionSetProratedValue;

@Component
public class XStreamHelper {

    private XStream xstream;

    public XStreamHelper() {
        super();
        xstream = new XStream();
        xstream.alias("rules", Set.class);
        xstream.alias("rule", Rule.class);
        xstream.aliasAttribute(Rule.class, "description", "description");
        xstream.aliasAttribute(Rule.class, "index", "index");
        xstream.aliasAttribute(Rule.class, "entity", "entity");
        xstream.addImplicitCollection(Rule.class, "trueFunctions");

        xstream.alias("and", AndExpression.class);
        xstream.alias("or", OrExpression.class);

        xstream.alias("GreaterThan", GreaterThan.class);
        xstream.alias("LessThan", LessThan.class);

        xstream.alias("Add", FunctionAdd.class);
        xstream.aliasAttribute(FunctionAdd.class, "leftOpernad", "leftOpernad");
        xstream.aliasAttribute(FunctionAdd.class, "rightOpernad", "rightOpernad");
        xstream.aliasAttribute(FunctionAdd.class, "index", "index");
        

        xstream.alias("condition", BaseExpression.class);
        xstream.aliasAttribute(BaseExpression.class, "leftOpernad", "leftOpernad");
        xstream.aliasAttribute(BaseExpression.class, "rightOpernad", "rightOpernad");

        xstream.addImplicitCollection(AndExpression.class, "expressions");
        xstream.addImplicitCollection(OrExpression.class, "expressions");

        xstream.alias("job", JobDefinition.class);
        xstream.aliasAttribute(JobDefinition.class, "code", "code");
        xstream.aliasAttribute(JobDefinition.class, "description", "description");
        xstream.addImplicitCollection(JobDefinition.class, "steps");

        xstream.alias("step", StepDefinition.class);
        xstream.aliasAttribute(StepDefinition.class, "code", "code");
        xstream.aliasAttribute(StepDefinition.class, "index", "index");
        xstream.aliasAttribute(StepDefinition.class, "description", "description");
        xstream.addImplicitCollection(StepDefinition.class, "outputSqls");

        xstream.alias("OutputSql", OutputSql.class);
        xstream.aliasAttribute(OutputSql.class, "index", "index");
        xstream.aliasAttribute(OutputSql.class, "generatedKeyField", "generatedKeyField");
        
        
        xstream.alias("SetProratedValue", FunctionSetProratedValue.class);
        xstream.aliasAttribute(FunctionSetProratedValue.class, "valueField", "value");
        xstream.aliasAttribute(FunctionSetProratedValue.class, "maxUnitField", "maxUnit");
        xstream.aliasAttribute(FunctionSetProratedValue.class, "actualUnitField", "actualUnit");
        xstream.aliasAttribute(FunctionSetProratedValue.class, "variableField", "variable");
        
//        xstream.aliasAttribute(Entry.class, "key", "key");
        

//         xstream.registerConverter(new RuleConverter());
    }

    public String toXml(Object object) {
        return xstream.toXML(object);
    }

    public Object fromXml(String filePath) {

        return xstream.fromXML(new File(filePath));
    }

    public Object fromXml(InputStreamReader reader) {
        return xstream.fromXML(reader);
    }
}
