package com.kk.service.impl;

import com.kk.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MessageServiceImpl implements MessageService {

    private ArrayList<String> arrayList = new ArrayList<>();

    @Override
    public void sendMessage(String id) {
        arrayList.add(id);
        System.out.println("待发送短信的订单已纳入处理队列：" + id);
    }

    @Override
    public String doMessage() {
        return "发送短信完毕： " + arrayList.remove(0);
    }
}
