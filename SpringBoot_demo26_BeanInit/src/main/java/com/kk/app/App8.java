package com.kk.app;

import com.kk.config.SpringConfig8;
import com.kk.service.BookService;
import com.kk.service.impl.BookServiceImpl2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App8 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig8.class);
        applicationContext.register(BookServiceImpl2.class);
        BookService bookService = (BookService) applicationContext.getBean("bookService");
        bookService.check();
    }
}
