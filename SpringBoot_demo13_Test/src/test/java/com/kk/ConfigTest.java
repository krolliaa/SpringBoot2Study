package com.kk;

import com.kk.config.MgConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(value = {MgConfig.class})
public class ConfigTest {

    @Autowired
    private String msg1;

    @Test
    void msg() {
        System.out.println(msg1);
    }
}
