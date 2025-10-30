package com.bookmanager.bookmanagement.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String role; // optional, agar nahi diya to default USER
    private String email;
	
}
