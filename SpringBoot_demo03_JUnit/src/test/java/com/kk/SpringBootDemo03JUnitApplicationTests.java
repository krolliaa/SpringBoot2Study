package com.kk;

import com.kk.dao.BookDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootDemo03JUnitApplicationTests {

    @Autowired
    private BookDao bookDao;

    @Test
    void contextLoads() {
        bookDao.save();
        System.out.println("test...");
    }
}
