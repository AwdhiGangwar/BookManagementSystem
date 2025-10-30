package com.bookmanager.bookmanagement.controller;

import com.bookmanager.bookmanagement.entity.User;
import com.bookmanager.bookmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173") // React port (Vite)
public class UserController {

    @Autowired
    private UserService userService;

    // ✅ Get user by username/email
    @GetMapping("/{identifier}")
    public User getUser(@PathVariable String identifier) {
        return userService.findByUsernameOrEmail(identifier);
    }
}
