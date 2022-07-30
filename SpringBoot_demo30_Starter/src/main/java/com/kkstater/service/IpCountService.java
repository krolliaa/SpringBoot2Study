package com.kkstater.service;

import com.kkstater.util.IpProperties;
import com.kkstater.util.LogModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class IpCountService {
    private Map<String, Integer> ipCountMap = new HashMap<String, Integer>();

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private IpProperties ipProperties;

    public void count() {
        //获取访问 ip
        String ip = httpServletRequest.getRemoteAddr();
        //判断 map 中的 ip 是否是第一次
        Integer count = ipCountMap.get(ip);
        if (count == null) {
            ipCountMap.put(ip, 1);
        } else {
            ipCountMap.put(ip, count + 1);
        }
    }

    //@Scheduled(cron = "0/5 * * * * ?")
    @Scheduled(cron = "0/#{ipProperties.cycle} * * * * ?")
    public void print() {
        if (ipProperties.getModel().equals(LogModule.DETAIL.getValue())) {
            System.out.println(" IP访问监控");
            System.out.println("+-----ip-address-----+--num--+");
            for (Map.Entry<String, Integer> entry : ipCountMap.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                System.out.println(String.format("|%18s |%5d |", key, value));
            }
            System.out.println("+--------------------+-------+");
        } else if (ipProperties.getModel().equals(LogModule.SIMPLE.getValue())) {
            System.out.println(" IP访问监控");
            System.out.println("+-----ip-address-----+");
            for (String key : ipCountMap.keySet()) {
                System.out.println(String.format("|%18s |", key));
            }
            System.out.println("+--------------------+");
        }
        //阶段内统计数据归零
        if (ipProperties.getCycleReset()) {
            ipCountMap.clear();
        }
    }
}
