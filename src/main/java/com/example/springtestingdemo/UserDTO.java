package com.example.springtestingdemo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {
	Long id;
	String name;
}
