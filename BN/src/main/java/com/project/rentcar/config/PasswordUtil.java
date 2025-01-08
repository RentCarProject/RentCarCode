package com.project.rentcar.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtil {

    // SHA-256을 이용하여 비밀번호를 암호화
    public static String encryptPassword(String password, String salt) {
        try {
            // SHA-256 해시 객체 생성
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            // Salt를 password와 합친 후 해시화
            messageDigest.update(salt.getBytes());
            byte[] hashedBytes = messageDigest.digest(password.getBytes());

            // 해시된 바이트 배열을 16진수로 변환
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();  // 암호화된 비밀번호 리턴
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Password encryption error", e);
        }
    }

    // Salt 생성 (랜덤한 값을 사용하여 Salt 생성)
    public static String generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];  // 16바이트 Salt
        secureRandom.nextBytes(salt);
        return new String(salt);  // 생성된 Salt 반환
    }
}
