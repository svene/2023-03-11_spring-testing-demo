package com.example.springtestingdemo.t2jpa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * See Baeldung article:
 * Configuration classes annotated with @TestConfiguration are excluded from component scanning.
 * Therefore we need to import it explicitly in every test where we want to @Autowire it.
 * We can do that with the @Import annotation:
 */
@TestConfiguration
public class MyContextConfiguration {
	@Value("${spring.datasource.url}")
	String jdbcUrl;

	@Value("${spring.datasource.username}")
	String username;

	@Value("${spring.datasource.password}")
	String password;

	@Bean
	public DataSource myDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		return dataSource;
	}


}
