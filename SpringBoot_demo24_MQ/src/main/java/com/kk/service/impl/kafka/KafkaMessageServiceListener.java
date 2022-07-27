package com.kk.service.impl.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageServiceListener {
    @KafkaListener(topics = {"kk"})
    public void onMessage(ConsumerRecord<String, String> consumerRecord) {
        System.out.println("Kafka 处理订单完毕：" + consumerRecord.value());
    }
}
