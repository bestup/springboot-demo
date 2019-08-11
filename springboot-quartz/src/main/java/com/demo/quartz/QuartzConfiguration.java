package com.demo.quartz;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.demo.service.OrderTimerService;
import com.demo.service.TaskTimerService;

//@Configuration
public class QuartzConfiguration {

	//配置定时任务1： TaskTimerService
	@Bean(name = "firstJobDetail")
    public MethodInvokingJobDetailFactoryBean firstJobDetail(TaskTimerService taskTimerService) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        
        /*
         *  是否并发执行
         *  例如每5s执行一次任务，但是当前任务还没有执行完，就已经过了5s了，
         *  如果此处为true，则下一个任务会bing执行，如果此处为false，则下一个任务会等待上一个任务执行完后，再开始执行
         */
        jobDetail.setConcurrent(false); 
        
        jobDetail.setName("taskTimerService-job"); 		// 任务的名字
        jobDetail.setGroup("timer-job"); 				// 任务的分组
        jobDetail.setTargetObject(taskTimerService); 	// 被执行的对象
        jobDetail.setTargetMethod("task"); 				// 被执行的方法
        return jobDetail;
    }
	
	// 配置任务触发器1： TaskTimerService
    @Bean(name = "firstTrigger")
    public CronTriggerFactoryBean firstTrigger(@Qualifier(value = "firstJobDetail")JobDetail firstJobDetail) {
        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
        tigger.setJobDetail(firstJobDetail);
        tigger.setCronExpression("0/2 * * * * ?"); 	// 什么是否触发，Spring Scheduler Cron表达式
        tigger.setName("timer-myJobTrigger");
        return tigger;
    }
    
    //配置定时任务2:OrderTimerService
    @Bean
    public MethodInvokingJobDetailFactoryBean secondJobDetail(OrderTimerService orderTimerService) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setConcurrent(false); 
        jobDetail.setName("taskTimerService-job1"); 		// 任务的名字
        jobDetail.setGroup("timer-job1"); 				// 任务的分组
        jobDetail.setTargetObject(orderTimerService); 	// 被执行的对象
        jobDetail.setTargetMethod("task1"); 				// 被执行的方法
        return jobDetail;
    }
    
    // 配置任务触发器2： OrderTimerService
    @Bean(name = "secondTrigger")
    public CronTriggerFactoryBean secondTrigger(@Qualifier(value = "secondJobDetail")JobDetail secondJobDetail) {
    	CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(secondJobDetail);
        // cron表达式
        trigger.setCronExpression("0/3 * * * * ?");
        return trigger;
    }
    
    /**
     * 配置调度器工厂Bean
     */
    @Bean(name = "schedulerFactory")
    public SchedulerFactoryBean schedulerFactory(@Qualifier(value = "firstTrigger")Trigger firstTrigger,
    		@Qualifier(value = "secondTrigger")Trigger secondTrigger) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        
        // 覆盖已存在的任务,设置是否任意一个已定义的Job会覆盖现在的Job。默认为false，即已定义的Job不会覆盖现有的Job。
        bean.setOverwriteExistingJobs(false);
        
        // 延时启动定时任务，避免系统未完全启动却开始执行定时任务的情况，秒
        bean.setStartupDelay(5);
        
        // 注册触发器
        bean.setTriggers(firstTrigger, secondTrigger);
        return bean;
    }
    
  
}
