package com.kk.listener;

import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationListener;

public class MyListener implements ApplicationListener<SpringApplicationEvent> {
    @Override
    public void onApplicationEvent(SpringApplicationEvent event) {
        System.out.println("===============Listener===============");
        System.out.println(event.getTimestamp());
        System.out.println(event.getSource());
        System.out.println(event.getClass());
    }
}
