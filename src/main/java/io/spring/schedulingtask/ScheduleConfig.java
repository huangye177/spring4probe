package io.spring.schedulingtask;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ScheduleConfig
{
    @Bean
    public ScheduleTask task()
    {
        return new ScheduleTask();
    }
}
