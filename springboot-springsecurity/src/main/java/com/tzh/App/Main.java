package com.tzh.App;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Main {
    public static void main(String[] args){
    	Main mian = new Main();
    	mian.jiami();
//    	mian.jiemi();
    	
    }
    
    public void jiami(){
    	 PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
         String encode = passwordEncoder.encode("admin");
         System.out.println(encode);
    }
    public void jiemi(){
    	String pass = "$2a$10$gtot3WsUVRAcngGVupDWcOowgZvwAZUdefKrFvGu0u5mVWwh.lZtO";
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashPass = bcryptPasswordEncoder.encode(pass);
        System.out.println(hashPass);
        boolean f = bcryptPasswordEncoder.matches("$2a$10$gtot3WsUVRAcngGVupDWcOowgZvwAZUdefKrFvGu0u5mVWwh.lZtO",hashPass);
        System.out.println(f);
//        http://www.147xs.com/book/2551/4069364.html
    }
}
