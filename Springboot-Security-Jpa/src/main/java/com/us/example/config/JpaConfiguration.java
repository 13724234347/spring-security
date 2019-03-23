package com.us.example.config;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
 
/**
 * 
 * @作者 林水桥
 * 2018年8月25日上午9:16:55
 * @description JPA基础配置类
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
/**启用事务*/
@EnableTransactionManagement(proxyTargetClass=true)
/**DAO层*/
@EnableJpaRepositories(basePackages={"com.us.**.dao"})
/**实体类放置路径*/
@EntityScan(basePackages={"com.us.**.model"})
public class JpaConfiguration {
	@Bean
	PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor(){
		return new  PersistenceExceptionTranslationPostProcessor();
	}
}