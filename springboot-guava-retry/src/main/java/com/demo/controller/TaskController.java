package com.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.service.TaskService;

/**
 * 测试controller,采用postman请求测试
 */

@RestController
public class TaskController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TaskService taskService; 
	
	@Autowired
	private ThreadPoolTaskExecutor myThradPool;	//从池中取线程执行

	/**
	 * 利用线程池中的线程执行推送重试机制
	 */
	@RequestMapping("/taskAsync")
	public String taskAsyncPool() {
		
		log.info("本地保存信息成功------------，开始推送数据---------");

		String url = "www.baidu.com/url";
		String content = "pushdata-jsonString";
		
		//设置推送线程执行重试需要的数据
		taskService.setUrl(url);
		taskService.setContent(content);
		Thread task = new Thread(taskService);
		
		//数据推送异步执行，不影响正常的数据返回流程
		myThradPool.execute(task);

		log.info("本地返回数据到前端-----------数据推送仍在进行 ");
		return "success";
	}
	
	/**
	 * 这里为了确认线程间数据是否安全，可以每次请求传入不同的参数进行测试对比
	 */
	@RequestMapping("/taskAsync1")
	public String taskAsyncPoolPush(String url, String content) {
		
		log.info("本地保存信息成功------------，开始推送数据---------");
		
		//设置推送线程执行重试需要的数据
		taskService.setUrl(url);
		taskService.setContent(content);
		Thread task = new Thread(taskService);
		
		//数据推送异步执行，不影响正常的数据返回流程
		myThradPool.execute(task);

		log.info("本地返回数据到前端-----------数据推送仍在进行 ");
		return "success";
	}

}
