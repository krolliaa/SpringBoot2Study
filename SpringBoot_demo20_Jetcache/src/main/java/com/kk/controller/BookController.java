package com.kk.controller;

import com.alicp.jetcache.anno.*;
import com.kk.pojo.Book;
import com.kk.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("{id}")
    @Cached(name = "book_", key = "#id", expire = 3600, timeUnit = TimeUnit.SECONDS, cacheType = CacheType.REMOTE)
    //@CacheRefresh(refresh = 1)
    public Book get(@PathVariable Integer id) {
        return bookService.getById(id);
    }

    @PostMapping
    public boolean save(@RequestBody Book book) {
        return bookService.save(book);
    }

    @PutMapping
    @CacheUpdate(name = "book_", key = "#book.id", value = "#book")
    public boolean update(@RequestBody Book book) {
        return bookService.update(book);
    }

    @DeleteMapping("{id}")
    @CacheInvalidate(name = "book_", key = "#id")
    public boolean delete(@PathVariable Integer id) {
        return bookService.delete(id);
    }

    @GetMapping
    public List<Book> getAll() {
        return bookService.getAll();
    }
}
