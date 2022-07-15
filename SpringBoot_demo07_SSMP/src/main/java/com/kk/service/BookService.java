package com.kk.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kk.pojo.Book;

import java.util.List;

public interface BookService {
    Boolean save(Book book);
    Boolean update(Book book);
    Boolean delete(Integer id);
    Book getBookById(Integer id);
    List<Book> getAll();
    IPage<Book> getPage(int current, int pageSize);
}
