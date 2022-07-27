package com.kk.service.impl.rabbitmq.direct;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

//@Component
public class RabbitMQMessageListener1 {
    @RabbitListener(queues = {"directQueue1"})
    //监听哪个消息队列需要给出
    public void rabbitMQReceive1(String id) {
        System.out.println("消息队列 1 One Listener 已完成短信发送业务，id = " + id);
    }
}
