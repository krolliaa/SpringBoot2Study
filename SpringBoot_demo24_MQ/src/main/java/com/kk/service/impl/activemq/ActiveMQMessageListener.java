package com.kk.service.impl.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

//@Component
public class ActiveMQMessageListener {
    @JmsListener(destination = "destination_id")
    @SendTo(value = "destination_send_to")
    public String listenAndReceiveMessage(String id) {
        System.out.println("自动监听并消费消息：" + id);
        return "new" + id;
    }

    @JmsListener(destination = "destination_send_to")
    public void listenAndReceiveMessage2(String newId) {
        System.out.println(newId);
    }
}
