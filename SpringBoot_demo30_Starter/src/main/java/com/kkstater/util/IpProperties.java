package com.kkstater.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "tools.ip")
@Component(value = "ipProperties")
public class IpProperties {
    //日志显示周期
    private Long cycle = 5L;
    //是否周期内充值数据
    private Boolean cycleReset = false;
    //日志输出模式：detail：详细模式，simple：极简模式 ---> 创建一个 LogModule 枚举类
    private String model = LogModule.DETAIL.getValue();
}