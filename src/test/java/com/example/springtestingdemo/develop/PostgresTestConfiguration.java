package com.example.springtestingdemo.develop;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@TestConfiguration
public class PostgresTestConfiguration {
	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgreSQLContainer() {
		return new PostgreSQLContainer<>("postgres");
	}

	@Bean // this bean seems to be necessary that MyJpaTest is working. Otherwise it cannot instantiate a DataSource
	public DataSource myDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl(postgreSQLContainer().getJdbcUrl());
		dataSource.setUsername(postgreSQLContainer().getUsername());
		dataSource.setPassword(postgreSQLContainer().getPassword());

		return dataSource;
	}

}
