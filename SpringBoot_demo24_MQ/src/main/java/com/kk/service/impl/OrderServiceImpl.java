package com.kk.service.impl;

import com.kk.service.MessageService;
import com.kk.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private MessageService messageService;

    @Override
    public void order(String id) {
        System.out.println("订单处理开始：" + id);
        messageService.sendMessage(id);
        System.out.println("订单处理完毕：" + id);
    }
}
