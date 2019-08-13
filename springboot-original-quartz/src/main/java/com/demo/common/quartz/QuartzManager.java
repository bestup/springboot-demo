package com.demo.common.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzManager {
	
	private static Logger log = LoggerFactory.getLogger(QuartzManager.class);
	
	private static SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
	
	/**
     * @Description: 添加一个定时任务
     * @param jobName 			任务名
     * @param jobGroupName  	任务组名
     * @param triggerName 		触发器名
     * @param triggerGroupName	 触发器组名
     * @param jobClass  		任务
     * @param cron   			时间设置，参考quartz说明文档
	 * @throws SchedulerException 
     */
	public static void addQuartzJob(String jobName, String groupName, String triggerName, 
			String triggerGroupName, Class jobClass, String cron) throws SchedulerException {
		
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		
		// 任务名，任务组，任务执行类
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, groupName).build();
		
		// 触发器
		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
		triggerBuilder.withIdentity(triggerName, triggerGroupName);
		triggerBuilder.startNow();	//开启任务触发器
		triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));	//触发器时间设定
		
		// 创建Trigger对象
        CronTrigger trigger = (CronTrigger) triggerBuilder.build();
		
        // 调度容器设置JobDetail和Trigger
        scheduler.scheduleJob(jobDetail, trigger);
		
        // 启动
        if (!scheduler.isShutdown()) {
        	scheduler.start();
        }
	}
	
	
	/**
     * @Description: 修改一个任务的触发时间
     * @param triggerName 		触发器名
     * @param triggerGroupName 	触发器组名
     * @param cron  			 时间设置，参考quartz说明文档
	 * @throws SchedulerException 
     */
    public static void modifyJobTime(String triggerName, String triggerGroupName, String cron) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (trigger == null) {
            return;
        }

        String oldTime = trigger.getCronExpression();
        if (!oldTime.equalsIgnoreCase(cron)) {
            /** 方式一 ：调用 rescheduleJob 开始 */
            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            // 创建Trigger对象
            trigger = (CronTrigger) triggerBuilder.build();
            // 方式一 ：修改一个任务的触发时间
            scheduler.rescheduleJob(triggerKey, trigger);
            /** 方式一 ：调用 rescheduleJob 结束 */
            
            //上下这两种做法都可以实现
            
            /** 方式二：先删除，然后在创建一个新的Job  */
            //JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
            //Class<? extends Job> jobClass = jobDetail.getJobClass();
            //removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
            //addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);
            /** 方式二 ：先删除，然后在创建一个新的Job */
        }
    }
    
    /**
     * @Description: 移除一个任务
     *
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @throws SchedulerException 
     */
    public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) throws SchedulerException {
 
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
        scheduler.pauseTrigger(triggerKey);// 停止触发器
        scheduler.unscheduleJob(triggerKey);// 移除触发器
        scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
    }
    
    /**
     * @throws SchedulerException 
     * @Description:启动所有定时任务
     */
    public static void startJobs() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.start();
    }
    
    /**
     * @throws SchedulerException 
     * @Description:关闭所有定时任务
     */
    public static void shutdownJobs() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        if (!scheduler.isShutdown()) {
        	scheduler.shutdown();
        }
    }
    
    /**
     * 安全关闭
     * 等待所有任务执行完毕后再关闭
     * @throws SchedulerException
     */
    public static void safeShutdown() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        int executingJobSize = scheduler.getCurrentlyExecutingJobs().size();
        log.info("安全关闭 当前还有" + executingJobSize + "个任务正在执行，等待完成后关闭");
        //等待任务执行完后安全关闭
        scheduler.shutdown(true);

        log.info("安全关闭 成功");
    }
    
}
