package com.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.service.TaskService;

@RestController
public class TaskController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TaskService taskService;
	
	@GetMapping("/task")
	public String task(String url, String content) {
		
		log.info("本地数据保存成功，开始远程推送********************");
		
		try {
			//线程异步推送数据（重试机制）
			taskService.task(url, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		log.info("返回前端，正在异步推送********************");
		return "success";
	}
	
	
}
