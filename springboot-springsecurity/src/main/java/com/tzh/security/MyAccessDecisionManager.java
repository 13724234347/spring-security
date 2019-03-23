package com.tzh.security;


import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;

@Service
public class MyAccessDecisionManager implements AccessDecisionManager {

    //decide 方法是判定是否拥有权限的决策方法
	//取得一个人所有权限,与我们从界面操作的连接加提交方式fjp
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        String url, method;
        AntPathRequestMatcher matcher;
        AntPathRequestMatcher matcher1;
        AntPathRequestMatcher matcher2;
        matcher = new AntPathRequestMatcher("/**/*.jsp");//如果发现跳转的是没权限页面
        matcher1 = new AntPathRequestMatcher("/noAuthority");
        matcher2 = new AntPathRequestMatcher("/*.jsp");
        if (matcher.matches(request)) {
           return;
        } else if (matcher1.matches(request)) {
            return;
        } else if (matcher2.matches(request)) {
            return;
        }
        else
        {
	        for (GrantedAuthority ga : authentication.getAuthorities()) {
	            if (ga instanceof MyGrantedAuthority) {
	                MyGrantedAuthority urlGrantedAuthority = (MyGrantedAuthority) ga;
	                url = urlGrantedAuthority.getPermissionUrl();
	                method = urlGrantedAuthority.getMethod();
	                matcher = new AntPathRequestMatcher(url);//表示我们需要跳转的页面
	                if (matcher.matches(request)) {
	                    //当权限表权限的method为ALL时表示拥有此路径的所有请求方式权利。
	                    if (method.equalsIgnoreCase(request.getMethod()) || "ALL".equalsIgnoreCase(method)) {
	                        return;
	                    }      
	                }
	            } else if (ga.getAuthority().equals("ROLE_ANONYMOUS")) {//未登录只允许访问 login 页面
	                matcher = new AntPathRequestMatcher("/login");
	                if (matcher.matches(request)) {
	                    return;
	                }
	            }
	        }
	        throw new AccessDeniedException("no right");  
        }
    } 
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }
    public boolean supports(Class<?> clazz) {
        return true;
    }
} 
