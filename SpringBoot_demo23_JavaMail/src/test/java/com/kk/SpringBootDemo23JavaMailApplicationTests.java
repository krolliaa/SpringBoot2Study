package com.kk;

import com.kk.service.SendMailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootDemo23JavaMailApplicationTests {

    @Autowired
    private SendMailService sendMailService;

    @Test
    void contextLoads() {
        sendMailService.sendMail();
    }

}
