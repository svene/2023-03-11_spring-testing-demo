package com.example.springtestingdemo;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * See Baeldung article:
 * Configuration classes annotated with @TestConfiguration are excluded from component scanning.
 * Therefore we need to import it explicitly in every test where we want to @Autowire it.
 * We can do that with the @Import annotation:
 */
@TestConfiguration
class DemoRestController3ContextConfiguration {

	@Bean
	public Postgres postgres() {
		Postgres postgres = new Postgres();
		return postgres;
	}

	@Bean
	public UserRepository userRepository() {
		UserRepository userRepository = new UserRepository(postgres());
		return userRepository;
	}

	@Bean
	public DemoRestController demoRestController() {
		return new DemoRestController(userRepository());
	}

}
