package com.kk.service.impl.rabbitmq.direct;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
public class RabbitMQMessageListener2 {
    @RabbitListener(queues = {"directQueue1"})
    public void rabbitMQReceive2(String id) {
        System.out.println("消息队列 1 Two Listener 已完成短信发送业务，id = " + id);
    }
}
