package com.kk.service.impl.rocketmq;

import com.kk.service.MessageService;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
public class RocketMQMessageServiceImpl implements MessageService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    @Override
    public void sendMessage(String id) {
        System.out.println("待发送短信的订单已纳入处理队列（rocketmq）：" + id);
        //rocketMQTemplate.convertAndSend("order_id", id);
        rocketMQTemplate.asyncSend("order_id", id, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("消息发送成功！");
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("消息发送失败！");
            }
        });
    }

    @Override
    public String doMessage() {
        return null;
    }
}
