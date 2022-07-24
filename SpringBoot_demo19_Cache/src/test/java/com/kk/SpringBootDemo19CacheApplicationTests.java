package com.kk;

import com.kk.pojo.Book;
import com.kk.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootDemo19CacheApplicationTests {

    @Autowired
    private BookService bookService;

    @Test
    void contextLoads() {
        Book book = bookService.getById(1);
        System.out.println(book);
    }

}
