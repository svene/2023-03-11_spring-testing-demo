package com.example.springtestingdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoRestController {

	// http://localhost:8080/users
	@GetMapping(value = "/users", produces = "application/json")
	public List<String> users() {
		return List.of("a", "b", "c");
}
}
