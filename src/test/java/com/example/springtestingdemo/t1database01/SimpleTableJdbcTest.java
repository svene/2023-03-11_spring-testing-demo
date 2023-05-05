package com.example.springtestingdemo.t1database01;

import com.example.springtestingdemo.develop.PostgresTestConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@Import(PostgresTestConfiguration.class)
class SimpleTableJdbcTest {

	private Connection conn;

	@Autowired
	DataSource dataSource;

	@BeforeEach
	public void beforeEach() throws SQLException {
		conn = dataSource.getConnection();
	}
	@AfterEach
	public void afterEach() throws SQLException {
		conn.close();
	}

	@Test
	void verifyInitialData() throws SQLException {
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM test_table WHERE name = 'test';");
			rs.next();
			int count = rs.getInt(1);
			assertThat(count).isEqualTo(1);
		}
	}
}
