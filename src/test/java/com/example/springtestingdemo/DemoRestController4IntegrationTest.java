package com.example.springtestingdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Like DemoRestController2IntegrationTest but with mocked 'UserRepository'.
// Do NOT bootstrap entire container:
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class DemoRestController4IntegrationTest {

	@TestConfiguration
	static class DemoRestController4ContextConfiguration {

		@Bean
		public Postgres postgres() {
			Postgres postgres = new Postgres();
			return postgres;
		}

		@MockBean
		UserRepository userRepository;

		@Bean
		public DemoRestController demoRestController() {
			return new DemoRestController(userRepository);
		}

	}

	private MockMvc mvc;

	@Autowired
	private DemoRestController controller;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void setup() throws SQLException {
		mvc = MockMvcBuilders.standaloneSetup(controller).build();
		Mockito.when(userRepository.getUsers())
			.thenReturn(Arrays.asList(UserDTO.builder().id(2L).name("Bart").build()));
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
			.andExpect(jsonPath("$[0].id").value("2"))
			.andExpect(jsonPath("$[0].name").value("Bart"));

	}
}
