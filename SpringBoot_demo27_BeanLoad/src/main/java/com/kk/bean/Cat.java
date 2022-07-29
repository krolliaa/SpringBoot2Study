package com.kk.bean;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

@Component(value = "Tom")
//@ConditionalOnMissingClass(value = {"com.kk.bean.Dog"})
@ConditionalOnClass(name = {"com.kk.bean.Mouse"})
@ConditionalOnBean(name = "Jerry")
public class Cat {
}
