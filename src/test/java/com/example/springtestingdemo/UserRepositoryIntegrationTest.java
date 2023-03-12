package com.example.springtestingdemo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringTestingDemoApplication.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class UserRepositoryIntegrationTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	void count() throws SQLException {
		// Verify that one row was inserted:
		long count = userRepository.getCount();
		assertThat(count).isEqualTo(1);
	}

	@Test
	void users() throws SQLException {
		// Verify that one row was inserted:
		List<UserDTO> actual = userRepository.getUsers();
		assertThat(actual).isEqualTo(Arrays.asList(UserDTO.builder().id(1L).name("test").build()));
	}
}
