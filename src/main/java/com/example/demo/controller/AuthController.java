package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User1;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerUser(@RequestBody User1 user) throws MessagingException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.registerUser(user);
        return "Registration successful! Please check your email for OTP.";
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String otp) {
        if (userService.verifyOtp(otp)) {
            return "Account activated successfully!";
        }
        return "Invalid OTP.";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        User1 user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        if (!user.isEnabled()) {
            throw new RuntimeException("Account is not activated");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }
}
