package com.tzh.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

/**
 * 过滤器，过滤静态资源和路径
 * Created by yangyibo on 17/1/19.
 */
@Service
//@WebFilter(urlPatterns={"/css/**","/js/**","/img/**"})
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {


    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        FilterInvocation fi = new FilterInvocation(request, response, chain);
		//invoke(fi);
        invoke(fi, request, response, chain); // 代码式过滤静态资源
    }


    public void invoke(FilterInvocation fi) throws IOException, ServletException {
		//fi里面有一个被拦截的url
		//里面调用MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限
		//再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
        	//执行下一个拦截器
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    /**
     * 代码式过滤静态资源
     * void
     * 2018年8月14日上午11:18:51
     */
    public void invoke(FilterInvocation fi, ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //fi里面有一个被拦截的url
        //里面调用MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限
        //再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够

        System.out.println(fi.getRequestUrl());
        if (fi.getRequestUrl().contains(".ico") || fi.getRequestUrl().startsWith("/js") || fi.getRequestUrl().startsWith("/picture") || fi.getRequestUrl().startsWith("/css-login")) {
            chain.doFilter(request, response);
        } else {
            InterceptorStatusToken token = super.beforeInvocation(fi);
            try {
                //执行下一个拦截器
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            } finally {
                super.afterInvocation(token, null);
            }
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;

    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }
    
//    public static void main(String[] args){
//    	int a[] = new int[25];
//    	System.out.println(a[24]);
//    	System.out.println(a[0]=0);
//    	System.out.println(a[25]=0);
//    	System.out.println(a[24]=0);
//    }
    
}
