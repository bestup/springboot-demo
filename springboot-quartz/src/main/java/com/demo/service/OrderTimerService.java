package com.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class OrderTimerService{
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	//定时执行的方法
	public void task1() {
		
		log.info("OrderTimerService 执行了+++++++++++++++++++++++++++++++++");
	}

}
