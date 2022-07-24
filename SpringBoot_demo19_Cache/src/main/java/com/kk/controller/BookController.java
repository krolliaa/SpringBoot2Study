package com.kk.controller;

import com.kk.pojo.Book;
import com.kk.service.BookService;
import com.kk.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private MsgService msgService;

    @GetMapping(value = "/{id}")
    public Book getById(@PathVariable Integer id) {
        Book book = bookService.getById(id);
        return book;
    }

    @GetMapping
    public String getCode(String telephone) {
        return msgService.getCode(telephone);
    }

    @PostMapping
    public Boolean checkCode(String telephone, String code) {
        return msgService.checkCode(telephone, code);
    }
}
