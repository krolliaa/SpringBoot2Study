package com.kk.service;

import com.kk.pojo.SimCard;

public interface SimCardService {
    public abstract String sendCode(String telephone);

    public abstract Boolean checkCode(SimCard simCard);
}
