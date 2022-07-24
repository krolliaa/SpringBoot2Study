package com.kk.service.impl;

import com.kk.pojo.SimCard;
import com.kk.service.SimCardService;
import com.kk.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class SimCardServiceImpl implements SimCardService {

    @Autowired
    private CodeUtil codeUtil;

    @Override
    @CachePut(value = "cacheSpace", key = "#telephone")
    public String sendCode(String telephone) {
        return codeUtil.generateCode(telephone);
    }

    @Override
    public Boolean checkCode(SimCard simCard) {
        String code = codeUtil.getCode(simCard.getTelephone());
        return code.equals(simCard.getCode());
    }
}
