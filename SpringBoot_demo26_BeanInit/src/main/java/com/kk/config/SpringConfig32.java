package com.kk.config;

import com.kk.bean.Cat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration(proxyBeanMethods = true)
@ImportResource(value = {"applicationContext1.xml"})
public class SpringConfig32 {
    @Bean
    public Cat cat() {
        return new Cat();
    }
}
