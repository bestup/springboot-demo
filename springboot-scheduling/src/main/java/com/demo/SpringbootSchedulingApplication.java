package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * SpringbootSchedulingApplication
 * 使用spring自带的scheduling定时调度任务相当于轻量级的Quartz,但是不支持分布式,若要实现分布式定时任务就得使用Quartz了
 */

@SpringBootApplication
@EnableScheduling 	//声明定时任务
public class SpringbootSchedulingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSchedulingApplication.class, args);
	}

}
