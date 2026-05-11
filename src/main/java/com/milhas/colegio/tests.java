package com.milhas.colegio;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class tests {
    public static void main(String[] args) {
        String password = "123456";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        System.out.println(encodedPassword);
        System.out.println("------------------");
        boolean isMatch =
                passwordEncoder.matches("123456", encodedPassword);

        System.out.println(isMatch);
    }
}