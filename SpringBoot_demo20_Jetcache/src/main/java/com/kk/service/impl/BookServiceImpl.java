package com.kk.service.impl;

import com.kk.mapper.BookMapper;
import com.kk.pojo.Book;
import com.kk.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public Book getById(Integer id) {
        return bookMapper.selectById(id);
    }

    @Override
    public boolean save(Book book) {
        return bookMapper.insert(book) > 0;
    }

    @Override
    public boolean update(Book book) {
        return bookMapper.updateById(book) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return bookMapper.deleteById(id) > 0;
    }

    @Override
    public List<Book> getAll() {
        return bookMapper.selectList(null);
    }
}
