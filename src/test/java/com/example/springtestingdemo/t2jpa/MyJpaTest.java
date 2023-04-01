package com.example.springtestingdemo.t2jpa;

import com.example.springtestingdemo.common.DbInitializer;
import com.example.springtestingdemo.common.PostgresInitializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

// Do NOT bootstrap entire container:
@ExtendWith(SpringExtension.class)
@Import({MyContextConfiguration.class, DbInitializer.class})
@ContextConfiguration(initializers = PostgresInitializer.class)
public class MyJpaTest {

	@Autowired
	DbInitializer dbInitializer;


	@Test
	void t1() {
		assertThat(dbInitializer).isNotNull();
	}

	@Test
	void t2() {
		dbInitializer.initialize();
	}
}
