package com.example.springtestingdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// JPA

// Like DemoRestController2IntegrationTest but with mocked 'UserRepository'.
// Do NOT bootstrap entire container:
@ExtendWith(SpringExtension.class)
//@DataJpaTest()
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class DemoRestController6IntegrationTest {

	@TestConfiguration
	static class DemoRestController4ContextConfiguration {

		@Bean
		public static Postgres postgres() {
			Postgres postgres = new Postgres();
			return postgres;
		}

//		@Bean
//		public DemoRestController demoRestController() {
//			return new DemoRestController(userRepository);
//		}

//		public static DataSource dataSource() {
//			return new
//		}

		/**
		 * See https://www.baeldung.com/spring-dynamicpropertysource
		 * https://www.baeldung.com/spring-boot-testcontainers-integration-test
		 */
		@DynamicPropertySource
		public static void initDataSource(DynamicPropertyRegistry registry) {
			var postgres = postgres();
			registry.add("spring.datasource.url", () -> String.format("jdbc:postgresql://localhost:%d/prop", postgres.container.getFirstMappedPort()));
			registry.add("spring.datasource.username", () -> Postgres.USERNAME);
			registry.add("spring.datasource.password", () -> Postgres.PASSWORD);

		}
	}

//	@Autowired
//	private Postgres postgres;


	private MockMvc mvc;

//	@Autowired
//	private DemoRestController controller;

//	@Autowired
//	private TestEntityManager em;

	@Autowired
	private UserJPARepository repository;

	@BeforeEach
	void setup() throws SQLException {
//		mvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	/**
	 * see https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#spring-mvc-test-framework
	 */
	@Test
	void users() throws Exception {

		List<UserEntity> all = repository.findAll();
		assertThat(all.size()).isEqualTo(1);
//		assertThat(all).isEqualTo(Arrays.asList(new UserEntity()));

	}
}
