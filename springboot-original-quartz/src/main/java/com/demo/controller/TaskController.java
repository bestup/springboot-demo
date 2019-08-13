package com.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.common.job.SendEmailQuartzJob;
import com.demo.common.quartz.QuartzManager;

/**
 * 动态任务测试
 */

@RestController
public class TaskController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 添加一个任务
	 */
	@PostMapping("/addJob")
	public Map<String, String> addJob() {
		Map<String, String> responseMap = new HashMap<>();
		String jobName = "sendEmailJob";
		String groupName = "sendJob";
		String triggerName = "sendEmailTrigger";
		String triggerGroupName = "sendTrigger";
		Class jobClass = SendEmailQuartzJob.class;
		String cron = "0/5 * * * * ?";
		
		try {
			QuartzManager.addQuartzJob(jobName, groupName, triggerName, triggerGroupName, jobClass, cron);
		} catch (SchedulerException e) {
			e.printStackTrace();
			responseMap.put("message", "");
			responseMap.put("status", "fail");
			return responseMap;
		}
		
		responseMap.put("message", "");
		responseMap.put("status", "success");
		return responseMap;
	}
	
	
	/**
	 * 开启表中的所有任务
	 */
	@PostMapping("/startAllJobs")
	public Map<String, String> startAllJobs() {
		
		Map<String, String> responseMap = new HashMap<>();	
		try {
			QuartzManager.startJobs();
		} catch (SchedulerException e) {
			e.printStackTrace();
			responseMap.put("message", "");
			responseMap.put("status", "fail");
			return responseMap;
		}
		
		responseMap.put("message", "");
		responseMap.put("status", "success");
		return responseMap;
	}
	
	
	/**
	 * 	修改任务触发器时间
	 */
	@PostMapping("/modifyJobTime")
	public Map<String, String> modifyJobTime(String cron) {
		
		Map<String, String> responseMap = new HashMap<>();	
		try {
			String triggerName = "sendEmailTrigger";
			String triggerGroupName = "sendTrigger";
			Class jobClass = SendEmailQuartzJob.class;
			QuartzManager.modifyJobTime(triggerName, triggerGroupName, cron);
		} catch (SchedulerException e) {
			e.printStackTrace();
			responseMap.put("message", "");
			responseMap.put("status", "fail");
			return responseMap;
		}
		
		responseMap.put("message", "");
		responseMap.put("status", "success");
		return responseMap;
	}
	
	
	/**
	 * 移出任务
	 */
	@PostMapping("/removeJob")
	public Map<String, String> removeJob(String cron) {
		
		Map<String, String> responseMap = new HashMap<>();	
		try {
			String jobName = "sendEmailJob";
			String groupName = "sendJob";
			String triggerName = "sendEmailTrigger";
			String triggerGroupName = "sendTrigger";
			QuartzManager.removeJob(jobName, groupName, triggerName, triggerGroupName);
		} catch (SchedulerException e) {
			e.printStackTrace();
			responseMap.put("message", "");
			responseMap.put("status", "fail");
			return responseMap;
		}
		
		responseMap.put("message", "");
		responseMap.put("status", "success");
		return responseMap;
	}
	
	
	/**
	 * 关闭所有任务
	 */
	/**
	 * 移出任务
	 */
	@PostMapping("/safeShutdown")
	public Map<String, String> safeShutdown() {
		
		Map<String, String> responseMap = new HashMap<>();	
		try {
			QuartzManager.safeShutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
			responseMap.put("message", "");
			responseMap.put("status", "fail");
			return responseMap;
		}
		
		responseMap.put("message", "");
		responseMap.put("status", "success");
		return responseMap;
	}
	


}
