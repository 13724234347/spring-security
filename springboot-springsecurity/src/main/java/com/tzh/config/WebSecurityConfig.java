package com.tzh.config;


import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.tzh.security.CustomUserService;
import com.tzh.security.MyFilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Resource
	private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
	
	@Resource
	private CustomUserService customUserService;
	
	
	@Override
	public void configure(WebSecurity web)throws Exception {
		// 设置不拦截规则
		 web.ignoring().antMatchers("/css/**","/js/**","/img/**","/font-awesome/**");  
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)throws Exception {
		auth.userDetailsService(customUserService).passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		    .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class).csrf().disable()//在正确的位置添加我们自定义的过滤器  
	        .authorizeRequests()
	        .anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/jsp/login.jsp")//登入页面
			.failureUrl("/jsp/login.jsp?value=error")//登入失败
			.permitAll()//登录页面用户任意访问
			.loginProcessingUrl("/spring_security_check")
	        .usernameParameter("username")//对应页面输入框的name属性
	        .passwordParameter("password")//对应页面输入框的nam	e属性
	        .defaultSuccessUrl("/index.do")//登入成功后进入这个路径
	        .and()
	        .exceptionHandling().accessDeniedPage("/noAuthority")//跳转到没权限页面
	        .and()
			.logout().permitAll();
		http.headers().frameOptions().disable();
	}
}