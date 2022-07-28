package com.kk.app;

import com.kk.config.SpringConfig32;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App32 {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig32.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) System.out.println(beanDefinitionName);
        SpringConfig32 springConfig32 = applicationContext.getBean("springConfig32", SpringConfig32.class);
        System.out.println(springConfig32.cat());
        System.out.println(springConfig32.cat());
        System.out.println(springConfig32.cat());
    }
}
