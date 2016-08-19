package com.ust.dsms.billing.batch;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class DSMSJdbcBatchItemWriter implements ItemWriter<Map<String,Object>>, InitializingBean {

	private static final String[] KEY_COLUMN_NAMES = new String[]{"id"};

	private String sql;
	
	private NamedParameterJdbcOperations namedParameterJdbcTemplate;

	private String generatedKeyField;

	@Override
	public void write(List<? extends Map<String, Object>> items) throws Exception {
		for (Map<String, Object> map : items) {
			if(generatedKeyField!=null){
				KeyHolder generatedKeyHolder=new GeneratedKeyHolder();
				namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map) , generatedKeyHolder,KEY_COLUMN_NAMES);
				map.put(generatedKeyField, generatedKeyHolder.getKey().intValue());
			}
			else{
				namedParameterJdbcTemplate.update(sql,map);
			}
		}
	}


	
	/**
	 * Public setter for the data source for injection purposes.
	 *
	 * @param dataSource {@link javax.sql.DataSource} to use for querying against
	 */
	public void setDataSource(DataSource dataSource) {
		if (namedParameterJdbcTemplate == null) {
			this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		}
	}

	/**
	 * Public setter for the {@link NamedParameterJdbcOperations}.
	 * @param namedParameterJdbcTemplate the {@link NamedParameterJdbcOperations} to set
	 */
	public void setJdbcTemplate(NamedParameterJdbcOperations namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	

	/**
	 * Public setter for the query string to execute on write. The parameters
	 * should correspond to those known to the
	 * {@link ItemPreparedStatementSetter}.
	 * @param sql the query to set
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}



	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
	public void setGenartedKeyField(String generatedKeyField) {
		this.generatedKeyField=generatedKeyField;
	}
	

}
