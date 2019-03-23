package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.us.example.config.JpaConfiguration;

@ComponentScan(basePackages ="com.us.example")
@SpringBootApplication
@ImportAutoConfiguration(value=JpaConfiguration.class)
public class SpringbootSecurityJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSecurityJpaApplication.class, args);
	}
}
