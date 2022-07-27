package com.kk.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class InfoConfig implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        String currentDate = simpleDateFormat.format(date);
        builder.withDetail("startTime", currentDate);
        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("version", "0.0.1");
        infoMap.put("test", "test");
        builder.withDetails(infoMap);
    }
}
