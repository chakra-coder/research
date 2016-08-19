package com.ust.dsms.billing.batch;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.JobFlowBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.ust.dsms.billing.batch.definition.JobDefinition;
import com.ust.dsms.billing.batch.definition.OutputSql;
import com.ust.dsms.billing.batch.definition.StepDefinition;
import com.ust.dsms.billing.rule.processor.BillingProcessor;
import com.ust.dsms.billing.rules.utils.XStreamHelper;

@Configuration
@EnableBatchProcessing
public class BillingJobConfiguration {

    private static final Logger log = LoggerFactory.getLogger(BillingJobConfiguration.class);
    
    @Autowired
    private XStreamHelper xStreamHelper;
    
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;
    
    @Autowired
    @Qualifier("pgNamedJdbcTemplate")
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Bean
    public BillingProcessor processor() {
        return new BillingProcessor();
    }


    @Bean
    public JobExecutionListener listener() {
        return new BillingJobListener(new JdbcTemplate(dataSource));
    }

    @Bean
    public Job createBillingJob() throws Exception {
        
        InputStreamReader reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("job.xml"));
            
        JobDefinition jobDefinition = (JobDefinition) xStreamHelper.fromXml(reader);
        
        
        JobBuilder jobBuilder = jobBuilderFactory.get(jobDefinition.getCode());
        jobBuilder=jobBuilder.incrementer(new RunIdIncrementer());
        jobBuilder=jobBuilder.listener(listener());
        
        JobFlowBuilder flow=null;
        
        List<StepDefinition> steps = jobDefinition.getSteps();
        boolean firstStep=true;
        for (StepDefinition stepDefinition : steps) {
            if(firstStep){
                flow = jobBuilder.flow(createStep(stepDefinition));
                firstStep=false;
            }
            else{
                flow.next(createStep(stepDefinition));
            }
        }
        
        flow.next(jobCompletionTasklet());
        
        return flow.end().build();
    }
    
    public Step createStep(StepDefinition stepDefinition) {
        
        CompositeItemWriter<Map<String,Object>> compositeItemWriter = new CompositeItemWriter<>();
        List<ItemWriter<? super Map<String, Object>>> delegates=new ArrayList<>();
        compositeItemWriter.setDelegates(delegates);

        List<OutputSql> outputSqls = stepDefinition.getOutputSqls();
        for (OutputSql outputSql : outputSqls) {
            delegates.add(writer(outputSql));
        }
        
        return stepBuilderFactory.get(stepDefinition.getCode())
                .<Map<String, Object>,Map<String, Object>> chunk(10)
                .reader(reader(stepDefinition.getInputSql()))
                .processor(processor())
                .writer(compositeItemWriter)
                .build();
    }
    
    /**
     * 
     * @param sql
     * @return
     */
    public JdbcCursorItemReader<Map<String,Object>> reader(String sql) {
        JdbcCursorItemReader<Map<String, Object>> databaseReader = new JdbcCursorItemReader<>();
        databaseReader.setDataSource(dataSource);
        databaseReader.setSql(sql);
        databaseReader.setRowMapper(new ColumnMapRowMapper());
 
        return databaseReader;        
    }
    
    public DSMSJdbcBatchItemWriter writer(OutputSql outputSql) {
    	DSMSJdbcBatchItemWriter writer = new DSMSJdbcBatchItemWriter();
        writer.setJdbcTemplate(namedParameterJdbcTemplate);
        writer.setSql(outputSql.getSql());
        writer.setGenartedKeyField(outputSql.getGeneratedKeyField());
        writer.setDataSource(dataSource);
        return writer;
    }    
    

    
    @Bean
    public HazelcastInstance hazelCastInstance(){
        Config cfg = new Config("dsms-pricing-billing");
        return Hazelcast.newHazelcastInstance(cfg);
    }
    
    @Bean(name="variablePattern")
    public Pattern getVariablePattern(){
        return Pattern.compile("\\{([^}]*)\\}");
    }
    
    public TaskletStep jobCompletionTasklet() throws Exception {

        Tasklet tasklet = new Tasklet() {
            
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                log.info("************************* Job Completed *****************************");
                return RepeatStatus.FINISHED;
            }
        };
        
        return this.stepBuilderFactory.get("Tasklet").tasklet(tasklet).build();
    }    
}
