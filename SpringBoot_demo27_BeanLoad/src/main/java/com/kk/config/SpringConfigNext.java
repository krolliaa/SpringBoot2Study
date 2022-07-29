package com.kk.config;

import com.kk.bean.Cat;
import com.kk.bean.Mouse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(value = {Mouse.class, Cat.class})
public class SpringConfigNext {
    //@Bean
    //public Cat Tom() {
        //return new Cat();
    //}
}
