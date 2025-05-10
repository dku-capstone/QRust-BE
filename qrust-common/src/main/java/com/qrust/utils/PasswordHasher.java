package com.qrust.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public final class PasswordHasher {
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int SALT_BYTE_SIZE = 16;

    private PasswordHasher() {
    }

    public static String generateSalt() {
        byte[] bytes = new byte[SALT_BYTE_SIZE];
        new SecureRandom().nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String hash(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            String combined = password + salt;
            byte[] hashedBytes = md.digest(combined.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(HASH_ALGORITHM + " algorithm not found", e);
        }
    }
}
