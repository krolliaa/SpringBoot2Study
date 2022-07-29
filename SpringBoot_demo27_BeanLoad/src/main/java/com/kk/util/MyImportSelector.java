package com.kk.util;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        try {
            Class clazz = Class.forName("com.kk.bean.Mouse");
            System.out.println("clazz = " + clazz);
            if(clazz != null) return new String[]{"com.kk.bean.Cat"};
        } catch (ClassNotFoundException e) {
            return new String[0];
        }
        return null;
    }
}
