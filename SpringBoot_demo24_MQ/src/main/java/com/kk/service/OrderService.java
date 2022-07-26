package com.kk.service;

public interface OrderService {
    //这里传递的只有订单 id 一个值，只是一个模拟，实际上订单内容肯定不止 id 一个
    public abstract void order(String id);
}
