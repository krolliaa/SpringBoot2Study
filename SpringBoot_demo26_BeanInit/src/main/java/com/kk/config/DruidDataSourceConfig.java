package com.kk.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidDataSourceConfig {
    @Bean
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }
}
