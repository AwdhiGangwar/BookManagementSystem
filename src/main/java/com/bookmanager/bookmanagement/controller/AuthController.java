package com.bookmanager.bookmanagement.controller;
import com.bookmanager.bookmanagement.dto.LoginRequest;
import com.bookmanager.bookmanagement.dto.RegisterRequest;
import com.bookmanager.bookmanagement.entity.User;
import com.bookmanager.bookmanagement.service.UserService;
import com.bookmanager.bookmanagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173") // ✅ Add your frontend URL here
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    // ✅ Register user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            userService.registerUser(request);
            return ResponseEntity.ok("User registered successfully with email: " + request.getEmail());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    // ✅ Login using username or email
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // ✅ Authenticate credentials
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getIdentifier(), 
                            request.getPassword()
                    )
            );

            // ✅ Find user by username/email
            User user = userService.findByUsernameOrEmail(request.getIdentifier());
            if (user == null) {
                return ResponseEntity.status(404).body("User not found");
            }

            // ✅ Generate token with role
            String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

            // ✅ Prepare response
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("role", user.getRole());
            response.put("username", user.getUsername());

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid username/email or password");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Login failed: " + e.getMessage());
        }
    }
}
