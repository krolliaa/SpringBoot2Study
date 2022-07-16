package com.kk.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.mapper.BookMapper;
import com.kk.pojo.Book;
import com.kk.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IBookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

    @Autowired
    private BookMapper bookMapper;

    public IPage<Book> getPage(int current, int pageSize) {
        IPage<Book> iPage = new Page<>(current, pageSize);
        bookMapper.selectPage(iPage, null);
        return iPage;
    }
}
