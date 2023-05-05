package com.example.springtestingdemo;

import com.example.springtestingdemo.develop.PostgresTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(PostgresTestConfiguration.class)
class SpringTestingDemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
