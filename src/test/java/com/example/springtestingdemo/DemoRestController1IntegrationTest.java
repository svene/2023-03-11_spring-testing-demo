package com.example.springtestingdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest( // Bootstrap entire container, includes "@ExtendWith(SpringExtension.class)"
	webEnvironment = SpringBootTest.WebEnvironment.MOCK,
	classes = SpringTestingDemoApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class DemoRestController1IntegrationTest {

	@Autowired
	private MockMvc mvc;

	/**
	 * see https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#spring-mvc-test-framework
	 */
	@Test
	void users() throws Exception {
		mvc.perform(get("/users")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].id").value("1"))
			.andExpect(jsonPath("$[0].name").value("test"));

	}
}
