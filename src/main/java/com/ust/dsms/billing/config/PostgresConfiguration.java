package com.ust.dsms.billing.config;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@ConfigurationProperties("postgres")
public class PostgresConfiguration {
    private String username;

    private String password;

    private String url;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Bean(name = "pgDataSource")
    DataSource pgDataSource() throws SQLException {

        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        return dataSource;
    }

    @Bean(name = "pgJdbcTemplate")
    JdbcTemplate pgJdbcTemplate() throws SQLException {
        return new JdbcTemplate(pgDataSource());
    }

    @Bean(name = "pgNamedJdbcTemplate")
    NamedParameterJdbcTemplate pgNamedJdbcTemplate() throws SQLException {
        return new NamedParameterJdbcTemplate(pgDataSource());
    }

}
