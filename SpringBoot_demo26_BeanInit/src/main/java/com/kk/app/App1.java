package com.kk.app;

import com.kk.bean.Dog;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App1 {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext1.xml");
        Object cat = applicationContext.getBean("cat");
        System.out.println(cat);
        Object dog = applicationContext.getBean(Dog.class);
        System.out.println(dog);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for(String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }
}
