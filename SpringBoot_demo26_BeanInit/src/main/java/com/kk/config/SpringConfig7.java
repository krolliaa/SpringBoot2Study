package com.kk.config;

import com.kk.util.MyImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

@Import(value = {MyImportBeanDefinitionRegistrar.class})
public class SpringConfig7 {
}
