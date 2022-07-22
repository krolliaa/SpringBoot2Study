package com.kk;

import com.kk.pojo.Book;
import com.kk.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback(value = false)
public class ServiceTest {
    @Autowired
    private BookService bookService;

    @Test
    void testService() {
        Book book = new Book();
        book.setType("SpringBoot");
        book.setName("SpringBoot");
        book.setDescription("SpringBoot");
        bookService.save(book);
    }
}
