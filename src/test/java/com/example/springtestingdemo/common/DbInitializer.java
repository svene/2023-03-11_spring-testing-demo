package com.example.springtestingdemo.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class DbInitializer {
	@Value("${spring.datasource.url}")
	String jdbcUrl;

	@Value("${spring.datasource.username}")
	String username;

	@Value("${spring.datasource.password}")
	String password;

	public Connection newConnection() {
		try {
			return DriverManager.getConnection(jdbcUrl, username, password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void initialize() {
		var conn = newConnection();
		try {
			try (Statement stmt = conn.createStatement()) {
				stmt.executeUpdate("CREATE TABLE test_table (id SERIAL PRIMARY KEY, name VARCHAR(255));");
				stmt.executeUpdate("INSERT INTO test_table (name) VALUES ('test');");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
}
