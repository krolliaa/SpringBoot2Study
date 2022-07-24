package com.kk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringBootDemo19CacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemo19CacheApplication.class, args);
    }
}
