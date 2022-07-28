package com.kk.config;

import com.kk.bean.Cat;
import com.kk.bean.Dog;
import org.springframework.context.annotation.Import;

@Import(value = {Dog.class, Cat.class})
public class SpringConfig4 {
}
