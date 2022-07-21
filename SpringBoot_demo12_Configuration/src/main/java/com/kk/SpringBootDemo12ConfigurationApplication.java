package com.kk;

import com.alibaba.druid.pool.DruidDataSource;
import com.kk.pojo.Servers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(value = {Servers.class})
public class SpringBootDemo12ConfigurationApplication {

    @Bean
    @ConfigurationProperties(prefix = "datasource")
    public DruidDataSource getDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(SpringBootDemo12ConfigurationApplication.class, args);
        Servers servers = configurableApplicationContext.getBean(Servers.class);
        System.out.println(servers);
        DruidDataSource druidDataSource = configurableApplicationContext.getBean(DruidDataSource.class);
        System.out.println(druidDataSource.getDriverClassName());
        System.out.println(druidDataSource.getPassword());
    }
}
