package com.example.springtestingdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringTestingDemoApplication {

	public static void main(String[] args) {
		createSpringApplication().run(args);
	}

	public static SpringApplication createSpringApplication() {
		return new SpringApplication(SpringTestingDemoApplication.class);
	}

}
