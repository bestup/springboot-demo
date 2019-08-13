package com.demo.common.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/** QuartzServic
 *  配置系统启动就开始执行的固定定时任务
 */

@Component
public class TaskJobQuartz implements Job{
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		log.info("系统定时任务开启，正在执行****************************************");
		
	}
	
}
