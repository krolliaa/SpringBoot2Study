package com.kk;

import com.kk.cartoon.TomAndJerry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = {TomAndJerry.class})
public class MyApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(MyApp.class, args);
        TomAndJerry tomAndJerry = (TomAndJerry) configurableApplicationContext.getBean(TomAndJerry.class);
        tomAndJerry.playCartoon();
    }
}
