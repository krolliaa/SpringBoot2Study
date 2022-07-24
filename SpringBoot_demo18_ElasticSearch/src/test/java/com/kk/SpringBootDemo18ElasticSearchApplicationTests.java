package com.kk;

import com.kk.pojo.Book;
import org.apache.http.HttpHost;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class SpringBootDemo18ElasticSearchApplicationTests {

    private RestHighLevelClient restHighLevelClient;

    @Test
    void contextLoads() {
        Book book = new Book();
        book.setId(1);
        book.setName("《深入理解 Java 虚拟机》");
        book.setType("计算机科学");
        book.setDescription("作为一位Java程序员，你是否也曾经想深入理解Java虚拟机，但是却被它的复杂和深奥拒之门外？没关系，本书极尽化繁为简之妙，能带领你在轻松中领略Java虚拟机的奥秘。本书是近年来国内出版的唯一一本与Java虚拟机相关的专著，也是唯一一本同时从核心理论和实际运用这两个角度去探讨Java虚拟机的著作，不仅理论分析得透彻，而且书中包含的典型案例和最佳实践也极具现实指导意义。");
    }

    @BeforeEach
    void setUp() {
        //创建 RestHighLevelClientBuilder
        HttpHost httpHost = HttpHost.create("http://localhost:9200");
        RestClientBuilder restClientBuilder = RestClient.builder(httpHost);
        restHighLevelClient = new RestHighLevelClient(restClientBuilder);
    }

    @AfterEach
    void tearDown() throws IOException {
        //因为是手动创建的需要关闭客户端
        restHighLevelClient.close();
    }

    @Test
    void testESRestHighLevel() throws IOException {
        //创建索引【客户端操作】
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("books");
        restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);

    }
}
