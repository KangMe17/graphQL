package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class User1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String fullname;
    private String avatar;
    private LocalDate dob; // Date of Birth
    private String phone;
    private String address;

    private boolean enabled; // To check if OTP is verified

    private String role; // USER or ADMIN
    private String otp; // For activation OTP

    private LocalDate createdAt = LocalDate.now();
}
