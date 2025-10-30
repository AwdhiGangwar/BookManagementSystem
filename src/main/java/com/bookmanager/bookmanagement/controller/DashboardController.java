package com.bookmanager.bookmanagement.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:5173") // frontend origin
public class DashboardController {

    // ✅ Get dashboard data for logged-in user
    @GetMapping
    public Map<String, String> getDashboardData() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return Map.of(
                "message", "Welcome to your dashboard!",
                "username", username,
                "status", "Success"
        );
    }
}
