package com.example.springtestingdemo.common;

import com.example.springtestingdemo.SpringTestingDemoApplication;

public class TestApplication {
	public static void main(String[] args) {
		var application = SpringTestingDemoApplication.createSpringApplication();
		application.addInitializers(new PostgresInitializer());
		application.run(args);
	}
}
