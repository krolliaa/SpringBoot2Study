package com.kk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "memcached")
public class MemcachedYaml {
    private String servers;
    private int poolSize;
    private long opTimeout;
}
