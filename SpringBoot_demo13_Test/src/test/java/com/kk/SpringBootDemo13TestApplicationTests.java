package com.kk;

import com.kk.config.MgConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(args = {"--test.prop=testValue2"})
@Import(value = {MgConfig.class})
class SpringBootDemo13TestApplicationTests {

    @Value(value = "${test.prop}")
    private String testProp;

    @Autowired
    private String beanMsg;

    @Test
    void contextLoads() {
        System.out.println(testProp);
    }

}
