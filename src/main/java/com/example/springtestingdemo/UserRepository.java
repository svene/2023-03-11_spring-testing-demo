package com.example.springtestingdemo;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
@Slf4j
@AllArgsConstructor
public class UserRepository {
	private final Postgres postgres;

	@PostConstruct
	public void postConstruct() throws SQLException {
		Connection conn = getConnection();
		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("CREATE TABLE test_table (id SERIAL PRIMARY KEY, name VARCHAR(255));");
			stmt.executeUpdate("INSERT INTO test_table (name) VALUES ('test');");
		}
	}

	public long getCount() throws SQLException {
		try (Statement stmt = getConnection().createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM test_table WHERE name = 'test';");
			rs.next();
			int count = rs.getInt(1);
			return count;
		}
	}

	private Connection getConnection() throws SQLException {
		Connection conn = DriverManager.getConnection(
			postgres.container.getJdbcUrl(),
			postgres.container.getUsername(),
			postgres.container.getPassword()
		);
		return conn;
	}
}
