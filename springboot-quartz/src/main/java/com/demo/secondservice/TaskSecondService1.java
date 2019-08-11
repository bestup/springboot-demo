package com.demo.secondservice;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskSecondService1 implements Job {

private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("TaskSecondService1+++++++++执行了，   这是第二种写法，用spring集成的quartz框架, 来执行原生quartz的任务");
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		String str = (String) dataMap.get("str");
		log.info(str + "*******************************");
	}

}
