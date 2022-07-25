package com.kk;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.kk")
public class SpringBootDemo20JetcacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemo20JetcacheApplication.class, args);
    }

}
