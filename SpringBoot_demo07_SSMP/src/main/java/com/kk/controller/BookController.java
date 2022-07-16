package com.kk.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kk.pojo.Book;
import com.kk.service.impl.IBookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private IBookServiceImpl iBookService;

    @GetMapping
    public List<Book> getAll() {
        return iBookService.list();
    }

    @PostMapping
    public Boolean save(@RequestBody Book book) {
        return iBookService.save(book);
    }

    @PutMapping
    public Boolean update(@RequestBody Book book) {
        return iBookService.updateById(book);
    }

    @DeleteMapping(value = "/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return iBookService.removeById(id);
    }

    @GetMapping(value = "/{id}")
    public Book getById(@PathVariable Integer id) {
        return iBookService.getById(id);
    }

    @GetMapping(value = "/{current}/{pageSize}")
    public IPage<Book> getPage(@PathVariable Integer current, @PathVariable Integer pageSize) {
        return iBookService.getPage(current, pageSize);
    }
}