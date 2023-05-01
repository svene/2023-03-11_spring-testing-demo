package com.example.springtestingdemo.t2jdbctemplate;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

/**
 * See Baeldung article:
 * Configuration classes annotated with @TestConfiguration are excluded from component scanning.
 * Therefore we need to import it explicitly in every test where we want to @Autowire it.
 * We can do that with the @Import annotation:
 */
@TestConfiguration
public class MyContextConfiguration {
	@Autowired
	PostgreSQLContainer<?> postgreSQLContainer;

@Bean
DataSource myDataSource() {
	if (!postgreSQLContainer.isRunning()) {
		postgreSQLContainer.start(); // TODO: find a better solution
	}
	PGSimpleDataSource dataSource = new PGSimpleDataSource();
	dataSource.setURL(postgreSQLContainer.getJdbcUrl());
	dataSource.setUser(postgreSQLContainer.getUsername());
	dataSource.setPassword(postgreSQLContainer.getPassword());
	return dataSource;
}

	@Bean
	JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(myDataSource());
	}

}
