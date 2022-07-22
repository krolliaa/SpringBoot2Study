package com.kk.service.impl;

import com.kk.mapper.BookMapper;
import com.kk.pojo.Book;
import com.kk.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public int save(Book book) {
        return bookMapper.insert(book);
    }
}
