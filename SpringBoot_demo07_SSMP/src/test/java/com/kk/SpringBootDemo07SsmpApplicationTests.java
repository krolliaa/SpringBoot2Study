package com.kk;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kk.mapper.BookMapper;
import com.kk.pojo.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringBootDemo07SsmpApplicationTests {

    @Autowired
    private BookMapper bookMapper;

    @Test
    void contextLoads() {
        IPage iPage = new Page(2, 5);
        bookMapper.selectPage(iPage, null);
    }

    @Test
    void testSelectById() {
        Book book = bookMapper.selectById(10);
        System.out.println(book);
    }

    @Test
    void testSelectAll() {
        List<Book> bookList = bookMapper.selectList(null);
        bookList.stream().forEach(System.out::println);
    }

    @Test
    void testAddBook() {
        Book book = new Book();
        book.setType("Java");
        book.setName("《Java 核心技术卷》");
        book.setDescription("Java 秘器");
        bookMapper.insert(book);
    }

    @Test
    void testDeleteBook() {
        bookMapper.deleteById(36);
    }

    @Test
    void testUpdateBook() {
        Book book = new Book();
        book.setId(36);
        book.setType("JavaEE");
        book.setName("《Java 核心技术卷》");
        book.setDescription("Java 秘器");
        bookMapper.update(book, null);
    }

    @Test
    void testQueryWrapper() {
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("type", "JavaEE");
        bookMapper.selectList(queryWrapper);
        //也可以使用 LambdaQueryWrapper 解决变量名出错的问题
        //还可以使用 condition ，如果变量为 null 则不使用条件查询
        String type = "JavaEE";
        LambdaQueryWrapper<Book> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(type != null, Book::getType, type);
    }
}
