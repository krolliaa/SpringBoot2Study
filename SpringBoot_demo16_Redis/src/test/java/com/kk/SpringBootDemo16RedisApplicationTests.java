package com.kk;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class SpringBootDemo16RedisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {

    }

    @Test
    void set() {
        //问你准备操作哪种数据类型？
        //Cluster 集群、Hash、Geo 地理坐标、Value 最普通
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("k2", "v2");
    }

    @Test
    void get() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object object = valueOperations.get("k2");
        System.out.println(object);
    }

    @Test
    void hSet() {
         HashOperations hashOperations = redisTemplate.opsForHash();
         hashOperations.put("info", "name", "Tom");
    }

    @Test
    void hGet() {
        HashOperations hashOperations = redisTemplate.opsForHash();
        Object object = hashOperations.get("info", "name");
        System.out.println(object);
    }
}
