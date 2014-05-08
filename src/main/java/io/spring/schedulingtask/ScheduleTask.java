package io.spring.schedulingtask;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;

public class ScheduleTask
{
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 2000)
    public void reportCurrentTime()
    {
        System.out.println("The time is now " + dateFormat.format(new Date()));
    }
}