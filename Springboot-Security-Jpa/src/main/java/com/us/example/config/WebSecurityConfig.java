package com.us.example.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.us.example.security.MyFilterSecurityInterceptor;

/**
 * security权限配置
 * @作者 林水桥
 * 2018年8月25日上午9:21:55
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启security注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Autowired
    DataSource dataSource;
    
    @Autowired
    @Qualifier("customUserService")
    UserDetailsService userDatailsService;

    /**
     * 加密验证用户登录
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDatailsService).passwordEncoder(passwordEncoder()); //user Details Service验证
    }
    /**
     * 加密配置
     * PasswordEncoder
     * 2018年8月25日上午9:23:07
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * 设置静态资源不拦截
     */
    @Override
    public void configure(WebSecurity web)throws Exception {
        // 设置不拦截规则
        web.ignoring().antMatchers("/css/**","/js/**","/img/**","/css-login/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated() //任何请求,登录后可以访问
                .and()
                    .formLogin()
                    .loginPage("/loginPage")
                    .failureUrl("/loginPage?error")
                    .loginProcessingUrl("/spring_security_check")
                    .permitAll() //登录页面用户任意访问
                    .defaultSuccessUrl("/user/b")
                .and()
                    .rememberMe()
                    .rememberMeParameter("remember-me")
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(86400)
                .and()
                    .exceptionHandling().accessDeniedPage("/failure") //跳转到没权限页面
                .and()
                .logout().permitAll(); //注销行为任意访问
        // 在过滤器之前执行
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                .csrf().disable();


    }
    /**
     * rememberMe配置
     * PersistentTokenRepository
     * 2018年8月25日上午9:25:38
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        tokenRepositoryImpl.setDataSource(dataSource);
        return tokenRepositoryImpl;
    }
}

