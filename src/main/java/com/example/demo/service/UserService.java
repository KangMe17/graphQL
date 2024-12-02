package com.example.demo.service;

import java.util.Optional;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User1;
import com.example.demo.repository.User1Repository;

import jakarta.mail.internet.MimeMessage;

@Service
public class UserService {
    private final User1Repository userRepository;
    private final JavaMailSender mailSender;

    public UserService(User1Repository userRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    public User1 registerUser(User1 user) throws MessagingException {
        user.setOtp(generateOtp());
        user.setEnabled(false); // Disable until OTP is verified
        User1 savedUser = userRepository.save(user);

        sendOtpEmail(user.getEmail(), user.getOtp());
        return savedUser;
    }

    public boolean verifyOtp(String otp) {
        Optional<User1> user = userRepository.findByOtp(otp);
        if (user.isPresent()) {
            User1 verifiedUser = user.get();
            verifiedUser.setEnabled(true);
            verifiedUser.setOtp(null);
            userRepository.save(verifiedUser);
            return true;
        }
        return false;
    }

    private void sendOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject("Your OTP Code");
        helper.setText("Use the following OTP to activate your account: " + otp);

        mailSender.send(message);
    }

    private String generateOtp() {
        return String.valueOf((int) (Math.random() * 900000) + 100000); // 6-digit OTP
    }
}