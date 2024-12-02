package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private int quantity;
	private String desc;

	@ManyToOne
	@JoinColumn(name = "userid", nullable = false)
	private User user;
}