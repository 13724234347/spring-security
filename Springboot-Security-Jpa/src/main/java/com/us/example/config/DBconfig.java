package com.us.example.config;

import java.beans.PropertyVetoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.druid.pool.DruidDataSource;
/**
 * Created by yangyibo on 17/1/18.
 */
@Configuration
public class DBconfig {
    @Autowired
    private Environment env;

    /**
     * ali数据源
     * DruidDataSource
     * 2018年8月25日上午9:16:23
     */
    @Bean(name="dataSource")
    public DruidDataSource dataSource() throws PropertyVetoException {
//        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("ms.db.url"));
        dataSource.setUsername(env.getProperty("ms.db.username"));
        dataSource.setPassword(env.getProperty("ms.db.password"));
//        dataSource.setDriverClass(env.getProperty("ms.db.driverClassName"));
//        dataSource.setJdbcUrl(env.getProperty("ms.db.url"));
//        dataSource.setUser(env.getProperty("ms.db.username"));
//        dataSource.setPassword(env.getProperty("ms.db.password"));
//        dataSource.setMaxPoolSize(20);
//        dataSource.setMinPoolSize(5);
//        dataSource.setInitialPoolSize(10);
//        dataSource.setMaxIdleTime(300);
//        dataSource.setAcquireIncrement(5);
//        dataSource.setIdleConnectionTestPeriod(60);
//        dataSource.setMaxStatements(0);
//        dataSource.setCheckoutTimeout(100);

        return dataSource;
    }
}
