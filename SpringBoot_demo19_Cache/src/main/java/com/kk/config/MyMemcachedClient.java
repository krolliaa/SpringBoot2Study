package com.kk.config;

import com.kk.pojo.MemcachedYaml;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class MyMemcachedClient {

    @Autowired
    private MemcachedYaml memcachedYaml;

    @Bean
    public MemcachedClient getMemcachedClient() throws IOException {
        MemcachedClientBuilder memcachedClientBuilder = new XMemcachedClientBuilder(memcachedYaml.getServers());
        memcachedClientBuilder.setOpTimeout(memcachedYaml.getOpTimeout());
        memcachedClientBuilder.setConnectionPoolSize(memcachedYaml.getPoolSize());
        MemcachedClient memcachedClient = memcachedClientBuilder.build();
        return memcachedClient;
    }
}
