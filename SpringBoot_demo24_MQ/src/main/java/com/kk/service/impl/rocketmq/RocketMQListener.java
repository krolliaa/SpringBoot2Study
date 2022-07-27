package com.kk.service.impl.rocketmq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "order_id", consumerGroup = "group_rocketmq")
public class RocketMQListener implements org.apache.rocketmq.spring.core.RocketMQListener<String> {
    @Override
    public void onMessage(String id) {
        System.out.println("自动监听并消费消息：完成短信发送 ---> " + id);
    }
}
