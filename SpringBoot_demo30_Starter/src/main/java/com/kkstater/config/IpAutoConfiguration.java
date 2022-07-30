package com.kkstater.config;

import com.kkstater.util.IpProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.kkstater.service.IpCountService;
import org.springframework.context.annotation.Bean;

@EnableScheduling
//@EnableConfigurationProperties(IpProperties.class)
@Import(value = {IpProperties.class, SpringWebMvcConfig.class})
public class IpAutoConfiguration {
    @Bean
    public IpCountService ipCountService() {
        return new IpCountService();
    }
}
