package com.example.springtestingdemo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

// just JdbcTemplate

// Do NOT bootstrap entire container:
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DemoRestController5IntegrationTest {

	@TestConfiguration
	static class DemoRestController4ContextConfiguration {

		@Bean
		public Postgres postgres() {
			Postgres postgres = new Postgres();
			return postgres;
		}

		/**
		 * dynamically create 'dataSource' since the port for the Postgres URL needs to be determined dynamically
		 * from the container.
		 */
		@Bean()
		@Primary
		public DataSource dataSource() {
			var postgres = postgres();
			var url = String.format("jdbc:postgresql://localhost:%d/%s", postgres.container.getFirstMappedPort(), Postgres.DB_NAME);
			DataSource result = DataSourceBuilder.create()
				.url(url)
				.username(Postgres.USERNAME)
				.password(Postgres.PASSWORD)
				.build();
			return result;
		}

		@Bean(name = "applicationJdbcTemplate")
		public JdbcTemplate applicationDataConnection(){
			return new JdbcTemplate(dataSource());
		}
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeAll
	void beforeAll() throws SQLException {
		String sql = "CREATE TABLE users (id SERIAL PRIMARY KEY, name VARCHAR(255));";
		jdbcTemplate.execute(sql);
		sql = "INSERT INTO users (name) VALUES ('test');";
		jdbcTemplate.execute(sql);
	}

	/**
	 * see https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#spring-mvc-test-framework
	 */
	@Test
	void users() throws Exception {
		RowMapper<UserDTO> rowMapper = new BeanPropertyRowMapper<>(UserDTO.class);
		String sql = "SELECT * FROM users";
		var all = jdbcTemplate.query(sql, rowMapper);
		assertThat(all.size()).isEqualTo(1);
	}
}
