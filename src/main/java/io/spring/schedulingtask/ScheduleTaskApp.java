package io.spring.schedulingtask;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ScheduleTaskApp
{
    public static void main(String[] args) throws Exception
    {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

        // invoke the CONFIG
        ctx.register(ScheduleConfig.class);
        ctx.refresh();

        ScheduleTask task = ctx.getBean(ScheduleTask.class);
        task.reportCurrentTime();

    }
}
