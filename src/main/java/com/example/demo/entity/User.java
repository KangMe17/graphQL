package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String fullname;
	private String email;
	private String password;
	private String phone;

	@ManyToMany
	@JoinTable(name = "user_category", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories;
}