package com.example.demo.resolver;

import org.springframework.stereotype.Component;

import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

@Component
public class ProductResolver {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductResolver(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Iterable<Product> allProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(String title, int quantity, String desc, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Product product = new Product();
        product.setTitle(title);
        product.setQuantity(quantity);
        product.setDesc(desc);
        product.setUser(user);
        return productRepository.save(product);
    }
}