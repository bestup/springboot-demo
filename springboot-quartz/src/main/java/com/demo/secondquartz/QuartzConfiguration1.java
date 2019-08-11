package com.demo.secondquartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.demo.secondservice.TaskSecondService;
import com.demo.secondservice.TaskSecondService1;

@Configuration
public class QuartzConfiguration1 {
	
	//定义任务1
	@Bean
    public JobDetail taskSecondService(){
        return JobBuilder.newJob(TaskSecondService.class)	//OrderTimerService我们的业务类
                .withIdentity("OrderTimerService")				//可以给该OrderTimerService起一个id
                
                //每个JobDetail内都有一个Map，包含了关联到这个Job的数据，在Job类中可以通过context获取
                .usingJobData("msg", "Hello Quartz")	//关联键值对
                .storeDurably()	//即使没有Trigger关联时，也不需要删除该JobDetail
                .build();
    }
    
	//定义任务触发器1
    @Bean
    public Trigger printTimeJobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/2 * * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(taskSecondService())	//关联上述的JobDetail
                .withIdentity("quartzTaskService")	//给Trigger起个名字
                .withSchedule(cronScheduleBuilder)
                .build();
    }
    
    //定义任务2
    @Bean
    public JobDetail taskSecondService1(){
        return JobBuilder.newJob(TaskSecondService1.class)
                .withIdentity("OrderTimerService1")
                .usingJobData("msg", "Hello Quartz1")
                .usingJobData("str", "String value")
                .storeDurably()
                .build();
    }
    
    //定义任务触发器2
    @Bean
    public Trigger printTimeJobTrigger1() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/3 * * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(taskSecondService1())
                .withIdentity("quartzTaskService1")
                .withSchedule(cronScheduleBuilder)
                .build();
    }
    
}
