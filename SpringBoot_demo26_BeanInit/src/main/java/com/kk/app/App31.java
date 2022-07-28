package com.kk.app;

import com.kk.config.SpringConfig31;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App31 {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig31.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) System.out.println(beanDefinitionName);
        Object dogFactoryBean1 = applicationContext.getBean("dogFactoryBean");
        Object dogFactoryBean2 = applicationContext.getBean("dogFactoryBean");
        Object dogFactoryBean3 = applicationContext.getBean("dogFactoryBean");
        System.out.println(dogFactoryBean1);
        System.out.println(dogFactoryBean2);
        System.out.println(dogFactoryBean3);
    }
}
