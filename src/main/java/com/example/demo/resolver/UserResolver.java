package com.example.demo.resolver;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.demo.entity.Category;
import com.example.demo.entity.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Component
public class UserResolver {
	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;

	public UserResolver(UserRepository userRepository, CategoryRepository categoryRepository) {
		this.userRepository = userRepository;
		this.categoryRepository = categoryRepository;
	}

	public Iterable<User> allUsers() {
		return userRepository.findAll();
	}

	public Iterable<Category> allCategories() {
		return categoryRepository.findAll();
	}

	@Transactional
	public boolean addUserToCategory(Long userId, Long categoryId) {
		Optional<User> user = userRepository.findById(userId);
		Optional<Category> category = categoryRepository.findById(categoryId);

		if (user.isPresent() && category.isPresent()) {
			user.get().getCategories().add(category.get());
			userRepository.save(user.get());
			return true;
		}
		return false;
	}
}
