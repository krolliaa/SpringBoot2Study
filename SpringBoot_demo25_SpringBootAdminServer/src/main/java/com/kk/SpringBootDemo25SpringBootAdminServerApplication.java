package com.kk;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class SpringBootDemo25SpringBootAdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemo25SpringBootAdminServerApplication.class, args);
    }

}
