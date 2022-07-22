package com.kk.controller;

import com.kk.pojo.Book;
import com.kk.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public String getById() {
        System.out.println("GET BY ID......");
        return "Get By Id...SpringBoot";
    }


    @PostMapping
    public String save() {
        Book book = new Book();
        book.setName("SpringBoot");
        book.setType("SpringBoot");
        book.setDescription("SpringBoot");
        return String.valueOf(bookService.save(book));
    }

}
