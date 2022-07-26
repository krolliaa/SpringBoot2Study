package com.kk.service.impl.activemq;

import com.kk.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ActiveMqMessageServiceImpl implements MessageService {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Override
    public void sendMessage(String id) {
        System.out.println("待发送短信的订单已纳入处理队列：" + id);
        jmsMessagingTemplate.convertAndSend("destination_id", id);
    }

    @Override
    public String doMessage() {
        return "发送短信完毕： " + jmsMessagingTemplate.receiveAndConvert("destination_id", String.class);
    }
}
