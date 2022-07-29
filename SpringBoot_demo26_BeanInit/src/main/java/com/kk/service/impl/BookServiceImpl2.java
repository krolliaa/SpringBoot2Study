package com.kk.service.impl;

import com.kk.service.BookService;
import org.springframework.stereotype.Service;

@Service(value = "bookService")
public class BookServiceImpl2 implements BookService {
    @Override
    public void check() {
        System.out.println("Book Service 2....");
    }
}
