package com.kk.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyTask {
    @Scheduled(cron = "0/1 * * * * ?")
    public void printTask() {
        System.out.println("Task Running...");
    }
}
