package com.tzh.App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages ="com.tzh")
@EntityScan(basePackages = "com.tzh.entity")
@EnableJpaRepositories(basePackages = "com.tzh.dao")
@EnableAutoConfiguration
public class App{
	public static void main(String[] args) {
		SpringApplication.run(App.class,args);
	}
}
