package com.kk.service.impl;

import com.kk.pojo.SimCard;
import com.kk.service.SimCardService;
import com.kk.util.CodeUtil;
import net.oschina.j2cache.CacheChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimCardServiceImpl implements SimCardService {

    @Autowired
    private CodeUtil codeUtil;

    @Autowired
    private CacheChannel cacheChannel;

    @Override
    public String sendCode(String telephone) {
        //获取验证码
        String code = codeUtil.generateCode(telephone);
        cacheChannel.set("sms", telephone, code);
        return code;
    }

    @Override
    public Boolean checkCode(SimCard simCard) {
        //从缓存中提取验证码
        String code = cacheChannel.get("sms", simCard.getTelephone()).asString();
        //跟前端用户传递的验证码作比较
        return simCard.getCode().equals(code);
    }

}