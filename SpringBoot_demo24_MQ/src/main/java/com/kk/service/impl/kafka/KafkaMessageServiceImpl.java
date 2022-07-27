package com.kk.service.impl.kafka;

import com.kk.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageServiceImpl implements MessageService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendMessage(String id) {
        System.out.println("订单" + id + "已纳入 Kafka 消息队列");
        //发送
        kafkaTemplate.send("kk", id);
    }

    @Override
    public String doMessage() {
        return null;
    }
}
