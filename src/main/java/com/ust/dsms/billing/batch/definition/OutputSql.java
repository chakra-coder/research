package com.ust.dsms.billing.batch.definition;

public class OutputSql implements Comparable<OutputSql> {
    private int index;
    private String sql;
    private String generatedKeyField;
    
    @Override
    public int compareTo(OutputSql outputSql) {
        return outputSql.index-this.index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

	public String getGeneratedKeyField() {
		return generatedKeyField;
	}

	public void setGeneratedKeyField(String generatedKeyField) {
		this.generatedKeyField = generatedKeyField;
	}

	@Override
	public String toString() {
		return "OutputSql [index=" + index + ", generatedKeyField=" + generatedKeyField + ", sql=" + sql + "]";
	}


    
    
}
