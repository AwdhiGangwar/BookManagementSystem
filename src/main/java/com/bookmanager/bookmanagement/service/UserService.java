package com.bookmanager.bookmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookmanager.bookmanagement.dto.RegisterRequest;
import com.bookmanager.bookmanagement.entity.User;
import com.bookmanager.bookmanagement.repository.UserRepository;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Register new user
    public User registerUser(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // ✅ Default role = USER (frontend se kuch na aaye to)
        String role = (request.getRole() == null || request.getRole().trim().isEmpty())
                ? "USER"
                : request.getRole().toUpperCase();

        user.setRole(role);
        return userRepository.save(user);
    }

    // ✅ Find user by username or email
    public User findByUsernameOrEmail(String identifier) {
        return userRepository.findByUsernameOrEmail(identifier, identifier)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with username or email: " + identifier));
    }
}
