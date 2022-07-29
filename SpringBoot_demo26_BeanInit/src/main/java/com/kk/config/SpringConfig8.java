package com.kk.config;

import com.kk.service.impl.BookServiceImpl2;
import com.kk.util.MyBeanDefinitionRegistryPostProcessor;
import com.kk.util.MyImportBeanDefinitionRegistrarNext1;
import com.kk.util.MyImportBeanDefinitionRegistrarNext2;
import org.springframework.context.annotation.Import;

@Import(value = {MyBeanDefinitionRegistryPostProcessor.class,MyImportBeanDefinitionRegistrarNext2.class,MyImportBeanDefinitionRegistrarNext1.class,BookServiceImpl2.class})
public class SpringConfig8 {
}
