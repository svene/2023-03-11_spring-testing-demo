package com.example.springtestingdemo.t2jpa;

import com.example.springtestingdemo.common.DbInitializer;
import com.example.springtestingdemo.common.PostgresInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//	see also: https://stackoverflow.com/questions/68602204/how-to-combine-testcontainers-with-datajpatest-avoiding-code-duplication

@DataJpaTest() // Needed so that an instance from the interface 'UserRepository' will be instantiated by Spring
@Import({MyContextConfiguration.class, DbInitializer.class})
@ContextConfiguration(initializers = PostgresInitializer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Needed so that spring is not trying to use the embedded DB
public class MyJpaTest {

	@Autowired
	DbInitializer dbInitializer;

	@Autowired
	private UserRepository repository;

	@Test
	void t1() {
		assertThat(dbInitializer).isNotNull();
	}

	@Test
	void t2() {
		dbInitializer.initialize();
		List<UserEntity> all = new ArrayList<>();
		repository.findAll().forEach(all::add);
		assertThat(all.size()).isEqualTo(1);
	}
}
