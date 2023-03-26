package com.example.springtestingdemo.o2;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

public class PostgresInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>()
		.withReuse(true);

	public static Map<String, String> getProperties() {
		Startables.deepStart(Stream.of(/*redis, kafka, */postgres)).join();

		return Map.of(
			"spring.datasource.url", postgres.getJdbcUrl(),
			"spring.datasource.username", postgres.getUsername(),
			"spring.datasource.password",postgres.getPassword()
		);
	}

	@Override
	public void initialize(ConfigurableApplicationContext context) {
		context.getEnvironment().getPropertySources().addFirst(
			new MapPropertySource("testcontainers", (Map) getProperties())
		);
	}

}
