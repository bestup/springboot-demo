package com.demo.common.config;

import java.io.IOException;
import java.util.Properties;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Quartz的核心配置类
 */

@Configuration
public class QuartzConfiguration {
	
	@Autowired
	@Qualifier("taskJob")
	private JobDetail taskJob;
	
	@Autowired
	@Qualifier("taskTrigger")
	private Trigger taskTrigger;
	
	@Autowired
    private JobFactory jobFactory;
    
    /**
     * 配置任务调度器
     * 使用项目数据源作为quartz数据源
	 * 获取工厂bean(这里用一句话描述这个方法的作用)   
	 */
	@Bean(destroyMethod = "destroy")
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
      SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
      
      //将spring管理job自定义工厂交由调度器维护
      schedulerFactoryBean.setJobFactory(jobFactory);
      
      //设置覆盖已存在的任务，可选,QuartzScheduler启动时更新己存在的Job,这样就不用每次修改targetObject后删除qrtz_job_details表对应记录
      schedulerFactoryBean.setOverwriteExistingJobs(true);
     
      schedulerFactoryBean.setStartupDelay(2);	 //项目启动完成后，等待2秒后开始执行调度器初始化
      schedulerFactoryBean.setAutoStartup(false); //设置自行启动
      
      //设置数据源，使用与项目统一数据源
      //schedulerFactoryBean.setDataSource(dataSource);
      
      //设置上下文spring bean name
      schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
      
      schedulerFactoryBean.setQuartzProperties(quartzProperties());
      
      return schedulerFactoryBean;
    }

	@Bean
	public Scheduler scheduler() throws IOException, SchedulerException {
		SchedulerFactoryBean schedulerFactoryBean = schedulerFactoryBean();
		
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		
		//scheduler.scheduleJob(taskJob, taskTrigger);
		
		return scheduler;
	}
	
	/**
	 * 指定quartz.properties,从quartz.properties文件中读取Quartz配置属性
	 */
	@Bean
	public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
	
}
