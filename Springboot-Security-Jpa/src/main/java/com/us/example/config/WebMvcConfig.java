package com.us.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
/**
 * Created by yangyibo on 17/1/18.
 */
@Configuration

public class WebMvcConfig implements WebMvcConfigurer{

    /**
     * 通过mvc加载配置
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 配置指定路径映射
     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {  
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//    }

    /**
     * 配置mvc视图解析器，前缀+后缀
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/");
        viewResolver.setSuffix(".jsp");
        registry.viewResolver(viewResolver);
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/loginPage").setViewName("login");
    }
}
