package com.knowhy.crm.config;

import com.knowhy.crm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleConfig {
    @Autowired
    TaskService taskService;

    /**
     * 每天早晨8点  定时执行检查任务表中的任务，到期提醒
     */
    @Scheduled(cron = "0 0 8 * * ?")
    public void work(){
        taskService.doTaskTwice();
    }

    /**
     * 每个月1号凌晨1点 定时重置满意度调查表中的完成状态、发送状态以及年月
     */
    @Scheduled(cron = "0 0 1 1 * ?")
    public void restStatus(){
        taskService.restSatisfaction();
    }

}
