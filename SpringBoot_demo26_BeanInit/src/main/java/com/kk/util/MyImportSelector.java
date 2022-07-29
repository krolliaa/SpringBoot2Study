package com.kk.util;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //判断元注解是否存在 @Configuration 注解
        System.out.println("是否拥有 @Configuration 注解："+importingClassMetadata.hasAnnotation("org.springframework.context.annotation.Configuration"));
        //查看 @ComponentScan(basePackages = {}) 注解中配置参数
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes("org.springframework.context.annotation.ComponentScan");
        System.out.println(annotationAttributes);
        boolean flag = importingClassMetadata.hasAnnotation("org.springframework.context.annotation.Configuration");
        if(flag) {
            return new String[]{"com.kk.bean.Dog"};
        }else {
            return new String[]{"com.kk.bean.Mouse"};
        }
    }
}
