package com.example.demo.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.resolver.ProductResolver;
import com.example.demo.resolver.UserResolver;

@Controller
public class GraphQLController {
	private final UserResolver userResolver;
	private final ProductResolver productResolver;

	public GraphQLController(UserResolver userResolver, ProductResolver productResolver) {
		this.userResolver = userResolver;
		this.productResolver = productResolver;
	}

	@QueryMapping
	public Iterable<User> allUsers() {
		return userResolver.allUsers();
	}

	@QueryMapping
	public Iterable<Category> allCategories() {
		return userResolver.allCategories();
	}

	@QueryMapping
	public Iterable<Product> allProducts() {
		return productResolver.allProducts();
	}

	@MutationMapping
	public boolean addUserToCategory(@Argument Long userId, @Argument Long categoryId) {
		return userResolver.addUserToCategory(userId, categoryId);
	}

	@MutationMapping
	public Product createProduct(@Argument String title, @Argument int quantity, @Argument String desc,
			@Argument Long userId) {
		return productResolver.createProduct(title, quantity, desc, userId);
	}
}