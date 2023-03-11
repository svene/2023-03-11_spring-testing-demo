package com.example.springtestingdemo;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.SQLException;

@Service
@Slf4j
public class Postgres {

	public PostgreSQLContainer<?> container;

	@PostConstruct
	public void postConstruct() throws SQLException {
		log.info("init");
		// Start a Postgres container
		container = new PostgreSQLContainer<>("postgres:latest")
			.withUsername("postgres")
			.withPassword("password")
			.withDatabaseName("testdb");
		container.start();

	}

	@PreDestroy
	public void preDestroy() {
		container.stop();
	}
}
