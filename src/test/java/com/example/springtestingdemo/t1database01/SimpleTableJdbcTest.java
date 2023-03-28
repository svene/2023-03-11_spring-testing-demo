package com.example.springtestingdemo.t1database01;

import com.example.springtestingdemo.common.PostgresInitializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@ContextConfiguration(initializers = PostgresInitializer.class)
class SimpleTableJdbcTest {

	private Connection conn;

	@Value("${spring.datasource.url}")
	String jdbcUrl;

	@Value("${spring.datasource.username}")
	String username;

	@Value("${spring.datasource.password}")
	String password;


	@BeforeEach
	public void beforeEach() throws SQLException {
		conn = DriverManager.getConnection(jdbcUrl, username, password);
		System.out.println(jdbcUrl);
	}
	@AfterEach
	public void afterEach() throws SQLException {
		conn.close();
	}

	private void setupDatabaseStructure() throws SQLException {
		// Create a table and insert a row
		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("CREATE TABLE test_table (id SERIAL PRIMARY KEY, name VARCHAR(255));");
		}
	}

	private void setupDatabaseData() throws SQLException {
		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("INSERT INTO test_table (name) VALUES ('test');");
		}
	}

	@Test
	void verifyInitialData() throws SQLException {
		setupDatabaseStructure();
		setupDatabaseData();

		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM test_table WHERE name = 'test';");
			rs.next();
			int count = rs.getInt(1);
			assertThat(count).isEqualTo(1);
		}
	}
}
