package com.kk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootDemo22TaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemo22TaskApplication.class, args);
    }

}