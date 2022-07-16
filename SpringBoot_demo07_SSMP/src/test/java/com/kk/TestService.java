package com.kk;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kk.pojo.Book;
import com.kk.service.impl.BookServiceImpl;
import com.kk.service.impl.IBookServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestService {

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private IBookServiceImpl iBookService;

    @Test
    void testGetById() {
        System.out.println(bookService.getBookById(1));
    }

    @Test
    void testPage() {
        IPage<Book> iPage = bookService.getPage(2, 10);
        System.out.println(iPage.getCurrent());//获取当前页
        System.out.println(iPage.getSize());//获取当前条数
        System.out.println(iPage.getTotal());//获取总条数
        System.out.println(iPage.getRecords());//获取得到的记录
    }

    @Test
    void testMyBatisPlusService() {
        iBookService.getById(11);
    }
}
