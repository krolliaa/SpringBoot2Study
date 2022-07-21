package com.kk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MgConfig {
    @Bean
    public String msg1() {
        return "bean msg1";
    }

    @Bean
    public String msg2() {
        return "bean msg2";
    }
}
