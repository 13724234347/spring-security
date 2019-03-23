package com.tzh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("yiibai").password("123456").roles("USER");
	}

	//.csrf() is optional, enabled by default, if using WebSecurityConfigurerAdapter constructor
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/").access("hasRole('ROLE_USER')")
			.antMatchers("/welcome**").access("hasRole('ROLE_USER')")
			.antMatchers("/admin**").access("hasRole('ROLE_USER')")
			.and()
				.formLogin().loginPage("/login").failureUrl("/login?error")
				.usernameParameter("username").passwordParameter("password")
			.and()
				.logout().logoutSuccessUrl("/login?logout")
			.and().csrf().disable();
	}
	
	@Bean
    public static NoOpPasswordEncoder passwordEncoder() {
      return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}