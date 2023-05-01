package com.example.springtestingdemo.develop;

import com.example.springtestingdemo.SpringTestingDemoApplication;
import org.springframework.boot.SpringApplication;

public class DevApplication {
	public static void main(String[] args) {
		SpringApplication
			.from(SpringTestingDemoApplication::main)
			.with(PostgresTestConfiguration.class)
			.run(args);
	}

}
