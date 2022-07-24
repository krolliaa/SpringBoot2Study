package com.kk.service;

public interface MsgService {
    //获取验证码
    public abstract String getCode(String telephone);
    //校验验证码
    public abstract Boolean checkCode(String telephone, String code);
}
