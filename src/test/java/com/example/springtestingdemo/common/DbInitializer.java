package com.example.springtestingdemo.common;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class DbInitializer {
	@Autowired
	DataSource dataSource;

	@Autowired
	JdbcTemplate jdbcTemplate;

	private Connection newConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@PostConstruct
	public void initialize() {
		try (var conn = newConnection()) {
			System.out.println("===================================");

			Integer count = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
			System.out.println("count = " + count);

			String dbName = jdbcTemplate.queryForObject("SELECT current_database()", String.class);
			System.out.println("dbName = " + dbName);

			Statement stmt = conn.createStatement();
			stmt.executeUpdate("CREATE TABLE test_table (id SERIAL PRIMARY KEY, name VARCHAR(255));");
			stmt.executeUpdate("INSERT INTO test_table (name) VALUES ('test');");
			jdbcTemplate.execute("INSERT INTO test_table (name) VALUES ('test2');");
			count = jdbcTemplate.queryForObject("select count(*) from test_table", Integer.class);
			System.out.println("count = " + count);

			// Proof that Postgres and not H2 is used:
			// with postgres throws a PSQLException:
			// jdbcTemplate.execute("select top 10 * from test_table");

			// Correct Postgres syntax:
			jdbcTemplate.execute("select * from test_table LIMIT 10");

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
