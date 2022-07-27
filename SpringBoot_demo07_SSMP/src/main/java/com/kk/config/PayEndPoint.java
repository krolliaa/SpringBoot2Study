package com.kk.config;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "pay")
public class PayEndPoint {
    @ReadOperation
    public Object getPay() {
        return "唯物史观 辩证法 马克思 恩格斯 列宁 毛泽东！！！";
    }
}
