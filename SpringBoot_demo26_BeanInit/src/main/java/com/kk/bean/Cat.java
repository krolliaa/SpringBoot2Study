package com.kk.bean;

import org.springframework.stereotype.Component;

@Component(value = "Tom")
public class Cat {

    private int age;

    public Cat() {
    }

    public Cat(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "age=" + age +
                '}';
    }
}
