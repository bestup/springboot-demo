package com.demo.scheduling;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** TaskSchedulingService
 */

@Component
public class TaskSchedulingService {
	
   /**
    * 固定cron配置定时任务
    */
   @Scheduled(cron = "0/2 * * * * ?")
   public void task(){
       System.out.println("执行了TaskSchedulingService,时间为:"+new Date(System.currentTimeMillis()));
   }
	
}
