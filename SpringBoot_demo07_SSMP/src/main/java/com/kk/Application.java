package com.kk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        //String[] arg = {"--server.port = 8081", "--a=b"};
        System.getProperty("spring.devtools.restart.enabled", "true");
        SpringApplication.run(Application.class, args);
    }
}