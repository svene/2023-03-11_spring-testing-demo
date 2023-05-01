package com.example.springtestingdemo.t2jdbctemplate;

import com.example.springtestingdemo.common.DbInitializer;
import com.example.springtestingdemo.develop.PostgresTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

// https://www.baeldung.com/spring-jdbc-jdbctemplate

// Do NOT bootstrap entire container:
@ExtendWith(SpringExtension.class)
@Import({PostgresTestConfiguration.class, MyContextConfiguration.class, DbInitializer.class})
public class MyJdbcTemplateTest {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	void t1() {
		assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "test_table")).isEqualTo(2);
	}
}
