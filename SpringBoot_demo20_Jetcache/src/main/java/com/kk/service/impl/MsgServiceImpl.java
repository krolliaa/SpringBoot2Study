package com.kk.service.impl;

import com.kk.service.MsgService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class MsgServiceImpl implements MsgService {

    private HashMap<String, String> hashMap = new HashMap();

    @Override
    public String getCode(String telephone) {
        String code = telephone.substring(0, 6);
        if (hashMap.get(telephone) == null) {
            hashMap.put(telephone, code);
        }
        return hashMap.get(telephone);
    }

    @Override
    public Boolean checkCode(String telephone, String code) {
        return code.equals(hashMap.get(telephone));
    }
}
