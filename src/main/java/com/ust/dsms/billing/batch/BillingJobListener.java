package com.ust.dsms.billing.batch;

import com.hazelcast.core.IMap;
import com.ust.dsms.billing.rules.service.VariableService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class BillingJobListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(BillingJobListener.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	private VariableService variableService;

	@Autowired
	public BillingJobListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String insertIntoSql="insert into billing_process values()";
				return connection.prepareStatement(insertIntoSql, new String[] { "id" });
			}
		}, keyHolder);

		variableService.set("process_id", keyHolder.getKey().intValue(), null);

	}

	@Override
	public void afterJob(JobExecution jobExecution) {

		IMap<String, Object> longVariableMap = variableService.getMap("variableMap");
		// insertVariables(longVariableMap);

		variableService.shutdown();
		log.info("Job Completed");
	}

	/**
	 * 
	 */
	public void insertVariables(IMap<String, Object> variables) {

		String sql = "insert into variable_value(variable_key,variable_value)  values (?, ?) "
				+ "ON CONFLICT (variable_key) DO UPDATE SET variable_value = EXCLUDED.variable_value";

		Iterator<Entry<String, Object>> itr = variables.entrySet().iterator();

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Entry<String, Object> entry = itr.next();
				ps.setString(1, entry.getKey());
				ps.setString(2, String.valueOf(entry.getValue()));
			}

			@Override
			public int getBatchSize() {
				return variables.size();
			}
		});
	}
}
