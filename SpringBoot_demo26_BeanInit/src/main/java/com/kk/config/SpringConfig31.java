package com.kk.config;

import com.kk.bean.DogFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.kk.bean", "com.kk.config"})
public class SpringConfig31 {
    @Bean
    public DogFactoryBean dogFactoryBean() {
        return new DogFactoryBean();
    }
}
