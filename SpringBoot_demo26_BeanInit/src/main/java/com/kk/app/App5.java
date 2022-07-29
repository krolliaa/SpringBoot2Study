package com.kk.app;

import com.kk.bean.Cat;
import com.kk.bean.Dog;
import com.kk.config.SpringConfig5;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App5 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig5.class);
        applicationContext.registerBean("Tom", Cat.class, 0);
        applicationContext.registerBean("Tom", Cat.class, 1);
        applicationContext.registerBean("Tom", Cat.class, 2);
        applicationContext.register(Dog.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) System.out.println(beanDefinitionName);
        System.out.println(applicationContext.getBean("Tom"));
    }
}
