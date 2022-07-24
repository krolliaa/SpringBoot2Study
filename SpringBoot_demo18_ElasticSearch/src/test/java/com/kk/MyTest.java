package com.kk;

import com.alibaba.fastjson.JSON;
import com.kk.mapper.BookMapper;
import com.kk.pojo.Book;
import org.apache.http.HttpHost;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MyTest {

    private RestHighLevelClient restHighLevelClient;

    @BeforeEach
    void setUp() {
        HttpHost httpHost = HttpHost.create("http://localhost:9200");
        RestClientBuilder restClientBuilder = RestClient.builder(httpHost);
        restHighLevelClient = new RestHighLevelClient(restClientBuilder);
    }

    @AfterEach
    void tearDown() throws IOException {
        restHighLevelClient.close();
    }

    @Test
    void myRestHighLevelClient() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("books");
        restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
    }

    @Test
    void myCreateJsonRequest() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("books");
        String json = "{\n" +
                "    \"mappings\":{\n" +
                "        \"properties\":{\n" +
                "            \"id\":{\n" +
                "                \"type\":\"keyword\"\n" +
                "            },\n" +
                "            \"name\":{\n" +
                "                \"type\":\"text\",\n" +
                "                \"analyzer\":\"ik_max_word\",\n" +
                "                \"copy_to\":\"all\"\n" +
                "            },\n" +
                "            \"type\":{\n" +
                "              \t\"type\":\"keyword\"  \n" +
                "            },\n" +
                "            \"description\":{\n" +
                "                \"type\":\"text\",\n" +
                "                \"analyzer\":\"ik_max_word\",\n" +
                "                \"copy_to\":\"all\"\n" +
                "            },\n" +
                "            \"all\":{\n" +
                "                \"type\":\"text\",\n" +
                "                \"analyzer\":\"ik_max_word\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";
        createIndexRequest.source(json, XContentType.JSON);
        restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
    }

    @Autowired
    private BookMapper bookMapper;

    @Test
    void myCreateDocument() throws Exception {
        HttpHost httpHost = HttpHost.create("http://localhost:9200");
        RestClientBuilder restClientBuilder = RestClient.builder(httpHost);
        restHighLevelClient = new RestHighLevelClient(restClientBuilder);
        Book book = bookMapper.selectById(1);
        IndexRequest indexRequest = new IndexRequest("books").id(String.valueOf(book.getId()));
        String json = JSON.toJSONString(book);
        indexRequest.source(json, XContentType.JSON);
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        restHighLevelClient.close();
    }

    @Test
    void myBulkCreateDocument() throws IOException {
        List<Book> bookList = bookMapper.selectList(null);
        BulkRequest bulkRequest = new BulkRequest();
        for (Book book : bookList) {
            IndexRequest indexRequest = new IndexRequest("books").id(String.valueOf(book.getId()));
            String json = JSON.toJSONString(book);
            indexRequest.source(json, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
    }

    @Test
    void mySelectById() throws IOException {
        GetRequest getRequest = new GetRequest("books", "10");
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        String json = getResponse.getSourceAsString();
        System.out.println(json);
    }

    @Test
    void mySelect() throws IOException {
        SearchRequest searchRequest = new SearchRequest("books");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("all", "java"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits = searchResponse.getHits();
        for (SearchHit searchHit : searchHits) {
            String json = searchHit.getSourceAsString();
            Book book = JSON.parseObject(json, Book.class);
            System.out.println(book);
        }
    }
}