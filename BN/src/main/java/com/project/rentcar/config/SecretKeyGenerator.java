package com.project.rentcar.config;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[32]; // 32바이트 키 (256비트)
        random.nextBytes(key);
        String secretKey = Base64.getEncoder().encodeToString(key);
        System.out.println("Generated Secret Key: " + secretKey);
    }
}
