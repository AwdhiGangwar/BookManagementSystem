package com.bookmanager.bookmanagement.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;

public class GenerateJwtKey {
    public static void main(String[] args) {
        // Generate secure HS512 key
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        // Encode key as Base64 string (to use in application.yml)
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());

        System.out.println("Your secure HS512 key: " + base64Key);
    }
}
