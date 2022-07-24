package com.kk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.mapper.BookMapper;
import com.kk.pojo.Book;
import com.kk.service.BookService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    //private HashMap<Object, Book> hashMap = new HashMap();

    /*@Override
    public Book getById(Serializable id) {
        if (hashMap.get(id) == null) {
            Book book = super.getById(id);
            hashMap.put(id, book);
        }
        return hashMap.get(id);
    }*/

    @Override
    @Cacheable(value = "cacheSpace1111", key = "#id")
    public Book getById(Serializable id) {
        return super.getById(id);
    }
}
