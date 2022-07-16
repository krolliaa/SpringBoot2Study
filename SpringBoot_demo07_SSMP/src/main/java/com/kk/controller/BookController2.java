package com.kk.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kk.controller.utils.MessageAgreement;
import com.kk.pojo.Book;
import com.kk.service.impl.IBookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController2 {

    @Autowired
    private IBookServiceImpl iBookService;

    @GetMapping
    public MessageAgreement getAll() {
        return new MessageAgreement(true, iBookService.list());
    }

    @PostMapping
    public MessageAgreement save(@RequestBody Book book) {
        return new MessageAgreement(iBookService.save(book));
    }

    @PutMapping
    public MessageAgreement update(@RequestBody Book book) {
        return new MessageAgreement(iBookService.updateById(book));
    }

    @DeleteMapping(value = "/{id}")
    public MessageAgreement delete(@PathVariable Integer id) {
        return new MessageAgreement(iBookService.removeById(id));
    }

    @GetMapping(value = "/{id}")
    public MessageAgreement getById(@PathVariable Integer id) {
        return new MessageAgreement(true, iBookService.getById(id));
    }

    @GetMapping(value = "/{current}/{pageSize}")
    public MessageAgreement getPage(@PathVariable Integer current, @PathVariable Integer pageSize) {
        return new MessageAgreement(true, iBookService.getPage(current, pageSize));
    }
}