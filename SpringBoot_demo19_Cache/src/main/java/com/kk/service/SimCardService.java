package com.kk.service;

import com.kk.pojo.SimCard;
import net.rubyeye.xmemcached.exception.MemcachedException;

import java.util.concurrent.TimeoutException;

public interface SimCardService {
    public abstract String sendCode(String telephone) throws InterruptedException, TimeoutException, MemcachedException;
    public abstract Boolean checkCode(SimCard simCard) throws InterruptedException, TimeoutException, MemcachedException;
}
