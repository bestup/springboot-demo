package com.demo.common.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.demo.common.job.TaskJobQuartz;


/** SysQuartzConfig
 *  配置随项目启动的config
 */

@Component
public class TaskQuartzConfig {
	
	//定义任务1
	@Bean
    public JobDetail taskJob(){
        return JobBuilder.newJob(TaskJobQuartz.class)	//OrderTimerService我们的业务类
                .withIdentity("OrderTimerService")				//可以给该OrderTimerService起一个id
                
                //每个JobDetail内都有一个Map，包含了关联到这个Job的数据，在Job类中可以通过context获取
                .storeDurably()	//即使没有Trigger关联时，也不需要删除该JobDetail
                .build();
    }
    
	//定义任务1触发器1
    @Bean
    public Trigger taskTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/2 * * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(taskJob())	//关联上述的JobDetail
                .withIdentity("quartzTaskService")	//给Trigger起个名字
                .withSchedule(cronScheduleBuilder)
                .build();
    }


}
