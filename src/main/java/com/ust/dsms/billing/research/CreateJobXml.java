package com.ust.dsms.billing.research;

import com.ust.dsms.billing.batch.definition.JobDefinition;
import com.ust.dsms.billing.batch.definition.OutputSql;
import com.ust.dsms.billing.batch.definition.StepDefinition;
import com.ust.dsms.billing.rules.utils.XStreamHelper;

public class CreateJobXml {
    public static void main(String[] args) {
        JobDefinition job = new JobDefinition();
        job.setCode("J0001");
        job.setDescription("Generate invoice for customers");
        
        StepDefinition prepareData=new StepDefinition();
        prepareData.setIndex(1);
        prepareData.setDescription("Process cutomer data");
        prepareData.setInputSql("select * from customers");
        
        OutputSql outputSqlOne=new OutputSql();
        outputSqlOne.setIndex(1);
        outputSqlOne.setSql("INSERT INTO invoice (customer_id, item_id,item_price) VALUES (:customer_id, :item_id,:item_price)");

        prepareData.getOutputSqls().add(outputSqlOne);
        
        job.getSteps().add(prepareData);
        
        XStreamHelper  helper = new XStreamHelper();
        System.out.println(helper.toXml(job));
    }
}
