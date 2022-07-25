package com.kk.util;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CodeUtil {
    public static String generateCode(String telephone) {
        long password = 286574;
        long currentTime = System.currentTimeMillis();
        long hash = telephone.hashCode();
        long code = Math.abs(((hash ^ password) ^ currentTime) % 1000000);
        String[] values = {"000000", "00000", "0000", "000", "00", "0", ""};
        String stringCode = code + "";
        stringCode = values[stringCode.length()] + stringCode;
        return stringCode;
    }

    @Cacheable(value = "cacheSpace", key = "#telephone")
    public String getCode(String telephone) {
        return null;
    }
}
