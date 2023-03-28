package com.example.springtestingdemo.t1database00;

import com.example.springtestingdemo.common.PostgresInitializer;
import org.junit.jupiter.api.*;
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
class PostgresIntegrationTest {

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

	@Test
	void verifyThatDatabaseIsWorking() throws SQLException {
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT 1;");
			rs.next();
			int count = rs.getInt(1);
			assertThat(count).isEqualTo(1);
		}
	}
}
