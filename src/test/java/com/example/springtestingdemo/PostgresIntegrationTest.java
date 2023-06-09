package com.example.springtestingdemo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringTestingDemoApplication.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class PostgresIntegrationTest {

	@Autowired
	private Postgres postgres;

	private Connection conn;

	@BeforeEach
	public void setUp() throws SQLException {
		conn = DriverManager.getConnection(
			postgres.container.getJdbcUrl(),
			postgres.container.getUsername(),
			postgres.container.getPassword()
		);
	}
	@AfterEach
	public void tearDown() throws SQLException {
		conn.close();
	}

	@Test
	void t1() throws SQLException {
		// Verify that Postgres is up and running:
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT 1;");
			rs.next();
			int count = rs.getInt(1);
			assertThat(count).isEqualTo(1);
		}
	}
}
