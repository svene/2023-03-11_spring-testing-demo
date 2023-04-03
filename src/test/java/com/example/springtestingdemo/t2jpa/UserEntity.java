package com.example.springtestingdemo.t2jpa;

import jakarta.persistence.*;

@Entity(name = "test_table")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String name;
}
