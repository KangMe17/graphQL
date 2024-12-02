package com.example.demo.config;

import com.example.demo.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests(
				auth -> auth.requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated());
		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authManager(HttpSecurity http, BCryptPasswordEncoder encoder) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(username -> null) // Replace
																												// with
																												// actual
																												// UserDetailsService
				.passwordEncoder(encoder).and().build();
	}
}