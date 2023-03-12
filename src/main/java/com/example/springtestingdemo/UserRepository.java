package com.example.springtestingdemo;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
	public List<UserDTO> getUsers() throws SQLException {
		List<UserDTO> result = new ArrayList<>();
		try (Statement stmt = getConnection().createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM test_table;");
			while (rs.next()) {
				UserDTO dto = UserDTO.builder().id(rs.getLong(1)).name(rs.getString(2)).build();
				result.add(dto);
			}
			return result;
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
