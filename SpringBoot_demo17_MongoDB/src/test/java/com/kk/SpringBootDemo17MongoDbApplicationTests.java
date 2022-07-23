package com.kk;

import com.kk.pojo.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@SpringBootTest
class SpringBootDemo17MongoDbApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void contextLoads() {
        Book book = new Book();
        book.setId(1);
        book.setName("《深入理解 Java 虚拟机》");
        book.setType("计算机科学");
        book.setDescription("作为一位Java程序员，你是否也曾经想深入理解Java虚拟机，但是却被它的复杂和深奥拒之门外？没关系，本书极尽化繁为简之妙，能带领你在轻松中领略Java虚拟机的奥秘。本书是近年来国内出版的唯一一本与Java虚拟机相关的专著，也是唯一一本同时从核心理论和实际运用这两个角度去探讨Java虚拟机的著作，不仅理论分析得透彻，而且书中包含的典型案例和最佳实践也极具现实指导意义。");
        mongoTemplate.save(book);
    }

    @Test
    void testFind() {
        List<Book> bookList = mongoTemplate.findAll(Book.class);
        System.out.println(bookList);
    }
}
