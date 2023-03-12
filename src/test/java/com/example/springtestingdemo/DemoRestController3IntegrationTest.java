package com.example.springtestingdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Do NOT bootstrap entire container:
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@Import(DemoRestController3ContextConfiguration.class) // See javadocs in 'DemoRestController3ContextConfiguration' !
class DemoRestController3IntegrationTest {

	private MockMvc mvc;

	@Autowired
	private DemoRestController controller;

	@BeforeEach
	void setup() {
		mvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	/**
	 * see https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#spring-mvc-test-framework
	 */
	@Test
	void users() throws Exception {
		mvc.perform(get("/users")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(jsonPath("$[0].id").value("1"))
			.andExpect(jsonPath("$[0].name").value("test"));

	}
}
