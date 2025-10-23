package com.bookmanager.bookmanagement.service;

import com.bookmanager.bookmanagement.dto.RegisterRequest;

import com.bookmanager.bookmanagement.entity.User;
import com.bookmanager.bookmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getRole() != null && request.getRole().equalsIgnoreCase("ADMIN")) {
            user.setRole("ADMIN");
        } else {
            user.setRole("USER");
        }
        return userRepository.save(user);
    }
}