package com.kk.service.impl;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.kk.pojo.SimCard;
import com.kk.service.SimCardService;
import com.kk.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
public class SimCardServiceImpl implements SimCardService {

    @Autowired
    private CodeUtil codeUtil;

    @CreateCache(name = "jetCache", expire = 3600, timeUnit = TimeUnit.SECONDS)
    private Cache<String, String> jetCache;

    @Override
    public String sendCode(String telephone) {
        String code = codeUtil.generateCode(telephone);
        jetCache.put(telephone, code);
        return code;
    }

    @Override
    public Boolean checkCode(SimCard simCard) {
        String code = jetCache.get(simCard.getTelephone());
        return simCard.getCode().equals(code);
    }
}
