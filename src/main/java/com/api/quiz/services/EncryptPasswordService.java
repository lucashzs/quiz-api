package com.api.quiz.services;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptPasswordService {

    public static String encryptPassword(String rawPassword) {
        return new BCryptPasswordEncoder().encode(rawPassword);
    }
}
