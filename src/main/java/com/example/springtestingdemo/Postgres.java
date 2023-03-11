package com.example.springtestingdemo;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Service
@Slf4j
public class Postgres {

	public PostgreSQLContainer<?> container;

	@PostConstruct
	public void postConstruct() throws SQLException {
		log.info("init");
		// Start a Postgres container
		container = new PostgreSQLContainer<>("postgres:latest")
			.withUsername("postgres")
			.withPassword("password")
			.withDatabaseName("testdb");
		container.start();

		Connection conn = DriverManager.getConnection(container.getJdbcUrl(), container.getUsername(), container.getPassword());
		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("CREATE TABLE test_table (id SERIAL PRIMARY KEY, name VARCHAR(255));");
			stmt.executeUpdate("INSERT INTO test_table (name) VALUES ('test');");
		}
	}

	@PreDestroy
	public void preDestroy() {
		container.stop();
	}
}
