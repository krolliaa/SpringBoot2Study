package com.kk.service;

public interface MessageService {
    //要发送短信的订单纳入队列
    public abstract void sendMessage(String id);
    //处理短信
    public abstract String doMessage();
}
