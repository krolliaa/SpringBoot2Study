package com.kk.util;

import com.kk.bean.Cat;
import com.kk.bean.Mouse;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
//@Component
@ConfigurationProperties(prefix = "cartoon")
public class MyConfigurationProperties {
    private Cat cat;
    private Mouse mouse;
}
