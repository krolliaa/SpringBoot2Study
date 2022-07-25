package com.kk.service.impl;

import com.kk.pojo.SimCard;
import com.kk.service.SimCardService;
import com.kk.util.CodeUtil;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeoutException;

@Service
public class SimCardServiceImpl implements SimCardService {

    @Autowired
    private CodeUtil codeUtil;

    @Autowired
    private MemcachedClient memcachedClient;

    @Override
    public String sendCode(String telephone) throws InterruptedException, TimeoutException, MemcachedException {
        //获取验证码
        String code = codeUtil.generateCode(telephone);
        memcachedClient.set("cacheSpace::" + telephone, 10, code);
        return code;
    }

    @Override
    public Boolean checkCode(SimCard simCard) throws InterruptedException, TimeoutException, MemcachedException {
        //从缓存中提取验证码
        String code = memcachedClient.get("cacheSpace::" + simCard.getTelephone()).toString();
        System.out.println(code);
        //跟前端用户传递的验证码作比较
        return simCard.getCode().equals(code);
    }

    /*@Override*/
    /*@CachePut(value = "cacheSpace", key = "#telephone")*/
    /*public String sendCode(String telephone) {*/
    /*    return codeUtil.generateCode(telephone);*/
    /*}*/

    /*@Override*/
    /*public Boolean checkCode(SimCard simCard) {*/
    /*    String code = codeUtil.getCode(simCard.getTelephone());*/
    /*    return code.equals(simCard.getCode());*/
    /*}*/
}
