package com.kk;

import com.kk.pojo.MemcachedYaml;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(value = {MemcachedYaml.class})
public class SpringBootDemo19CacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemo19CacheApplication.class, args);
    }
}
