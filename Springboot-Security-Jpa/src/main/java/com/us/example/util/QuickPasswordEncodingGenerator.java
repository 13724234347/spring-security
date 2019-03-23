package com.us.example.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 
public class QuickPasswordEncodingGenerator {
 
    /**
     * security加密
     * @param args
     */
    public static void main(String[] args) {
            String password = "123456";
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            System.out.println(passwordEncoder.encode(password));
    }
 
}
