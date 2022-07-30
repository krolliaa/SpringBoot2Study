package com.kkstater.config;

import com.kkstater.interceptor.IpCountInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringWebMvcConfig implements WebMvcConfigurer {

    //只需要一个拦截器，交由容器管控，所以这里不需要 new
    @Bean
    public IpCountInterceptor ipCountInterceptor() {
        return new IpCountInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipCountInterceptor()).addPathPatterns("/**");
    }
}
