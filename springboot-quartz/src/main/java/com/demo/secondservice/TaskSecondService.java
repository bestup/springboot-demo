package com.demo.secondservice;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskSecondService implements Job{
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("TaskSecondService+++++++++执行了，   这是第二种写法，用spring集成的quartz框架, 来执行原生quartz的任务");
	}

}
