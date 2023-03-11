package com.example.springtestingdemo;

import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.*;

import static org.assertj.core.api.Assertions.*;

public class DatabaseTest {

	private static PostgreSQLContainer<?> postgres;
	private Connection conn;

	@BeforeAll
	public static void startContainer() {
		postgres = new PostgreSQLContainer<>("postgres:latest")
			.withUsername("postgres")
			.withPassword("password")
			.withDatabaseName("testdb");
		postgres.start();
	}

	@AfterAll
	public static void stopContainer() {
		postgres.stop();
	}

	@BeforeEach
	public void setUp() throws SQLException {
		conn = DriverManager.getConnection(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
	}
	@AfterEach
	public void tearDown() throws SQLException {
		conn.close();
	}

	private void setupDatabaseStructure() throws SQLException {
		// Create a table:
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

		// Verify that one row was inserted:
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM test_table WHERE name = 'test';");
			rs.next();
			int count = rs.getInt(1);
			assertThat(count).isEqualTo(1);
		}
	}
}
