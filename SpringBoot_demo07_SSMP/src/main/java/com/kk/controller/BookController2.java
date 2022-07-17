package com.kk.controller;

import com.kk.controller.utils.MessageAgreement;
import com.kk.pojo.Book;
import com.kk.service.impl.IBookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/books")
public class BookController2 {

    @Autowired
    private IBookServiceImpl iBookService;

    @GetMapping
    public MessageAgreement getAll() {
        //模拟添加失败的情况：return new MessageAgreement(false);
        return new MessageAgreement(true, iBookService.list());
    }

    @PostMapping
    public MessageAgreement save(@RequestBody Book book) throws Exception {
        if(book.getName().equals("11")) throw new Exception();
        Boolean flag = iBookService.save(book);
        return new MessageAgreement(flag, null, flag ? "添加成功！" : "添加失败！");
    }

    @PutMapping
    public MessageAgreement update(@RequestBody Book book) {
        return new MessageAgreement(iBookService.updateById(book));
    }

    @DeleteMapping(value = "/{id}")
    public MessageAgreement delete(@PathVariable Integer id) {
        //模拟删除失败的情况：return new MessageAgreement(false);
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