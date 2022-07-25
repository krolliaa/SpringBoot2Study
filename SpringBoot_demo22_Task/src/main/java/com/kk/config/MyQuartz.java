package com.kk.config;

import com.kk.quartz.MyQuartzJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyQuartz {
    @Bean
    public JobDetail jobDetail() {
        //storeDurably 是否做持久化
        return JobBuilder.newJob(MyQuartzJob.class).storeDurably().build();
    }

    @Bean
    public Trigger trigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/15/30/45 * * * * ?");
        return TriggerBuilder.newTrigger().forJob(jobDetail()).withSchedule(cronScheduleBuilder).build();
    }
}
