package com.kk;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootTest
class SpringBootDemo22TaskApplicationTests {

    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timer Task Running...");
            }
        };
        //从当前时间点开始每隔两秒运行一次
        timer.schedule(timerTask, 0, 2000);
    }

    @Test
    void contextLoads() {

    }

}
