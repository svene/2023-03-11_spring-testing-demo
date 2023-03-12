package com.example.springtestingdemo;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@AllArgsConstructor
public class DemoRestController {

	private final UserRepository userRepository;

	@GetMapping(value = "/users", produces = "application/json")
	public List<UserDTO> users() {
		try {
			return userRepository.getUsers();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
