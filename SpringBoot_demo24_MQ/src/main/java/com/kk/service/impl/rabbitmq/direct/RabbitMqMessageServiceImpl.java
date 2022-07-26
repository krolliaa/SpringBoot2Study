package com.kk.service.impl.rabbitmq.direct;

import com.kk.service.MessageService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqMessageServiceImpl implements MessageService {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void sendMessage(String id) {
        System.out.println("待发送短信的订单已纳入处理队列：" + id);
        //指定交换机、路由、要传递的值
        amqpTemplate.convertAndSend("directExchange", "direct1", id);
    }

    @Override
    public String doMessage() {
        return "发送短信完毕";
    }
}
