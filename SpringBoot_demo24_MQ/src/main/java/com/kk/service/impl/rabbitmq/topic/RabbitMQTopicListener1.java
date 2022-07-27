package com.kk.service.impl.rabbitmq.topic;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
public class RabbitMQTopicListener1 {
    @RabbitListener(queues = {"topicQueue1"})
    public void topicListener1(String id) {
        System.out.println("【Topic 消息队列 --- 1】 1 One Listener 已完成短信发送业务，id = " + id);
    }

    @RabbitListener(queues = {"topicQueue2"})
    public void topicListener2(String id) {
        System.out.println("【Topic 消息队列 --- 2】 1 One Listener 已完成短信发送业务，id = " + id);
    }
}